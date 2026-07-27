package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testData.TestData;
import java.util.List;
import java.util.Locale;

import static utils.helpers.Helpers.*;

public class BookedItineraryPageTest extends BaseTest {

    private List<String> testedData;

    @BeforeMethod
    public void initializePage() {
        searchHotelPage = homePage
                .openHomePageAndLoginWithValidRegisteredCredentials(registeredUserName, registeredPassword, homePageURL);

        int lastLocation = searchHotelPage.getNumOfLocations() - 1;
        int lastHotel = searchHotelPage.getNumOfHotels() - 1;
        int lastRoomType = searchHotelPage.getNumOfRoomTypes() - 1;
        int lastNumOfRooms = searchHotelPage.getNumOfNumsOfRooms() - 1;
        int lastAdults = searchHotelPage.getNumOfAdultsPerRoom() - 1;
        int lastChildren = searchHotelPage.getNumOfChildrenPerRoom() - 1;

        testedData = searchHotelPage.getTestedDataForSave(
                lastLocation, lastHotel, lastRoomType, lastNumOfRooms, lastAdults, lastChildren
        );

        selectHotelPage = searchHotelPage.selectAndFillAllFieldsAndClickSearch(
                lastLocation,
                lastHotel,
                lastRoomType,
                lastNumOfRooms,
                testedArrivalDate,
                testedDepartureDate,
                lastAdults,
                lastChildren
        );

        int tableRowSelection = selectHotelPage.getResultsTableRowCount() - 1;
        testedData.add(selectHotelPage.getTableCellValueByRowAndColumn(tableRowSelection, 8));
        testedData.add(selectHotelPage.getTableCellValueByRowAndColumn(tableRowSelection, 9));

        bookHotelPage = selectHotelPage.selectHotelFromTableAndClickContinueByIndex(tableRowSelection);

        testedData.add(bookHotelPage.getGstFixedFieldText());
        testedData.add(bookHotelPage.getFinalBilledPriceFixedFieldText());

        bookConfirmPage = bookHotelPage
                .fillBookHotelFormAndSubmit(
                        TestData.get("TestData.BillingData.Valid.FirstName"),
                        TestData.get("TestData.BillingData.Valid.LastName"),
                        TestData.get("TestData.BillingData.Valid.BillingAddress"),
                        TestData.get("TestData.BillingData.Valid.CreditCardNum"),
                        bookHotelPage.getLastOptionOfCreditCardTypeSelector(),
                        bookHotelPage.getLastOptionOfCreditCardExpiryMonthSelector(),
                        bookHotelPage.getLastOptionOfCreditCardExpiryYearSelector(),
                        TestData.get("TestData.BillingData.Valid.CreditCardNum"));

        testedData.add(bookConfirmPage.getOrderNoFixedFieldText());

        bookedItineraryPage =bookConfirmPage.staticBar.clickBookedItineraryCTA();
    }

    @Test
    public void verifyBookItineraryPageOpened(){
        Assert.assertTrue(bookedItineraryPage.isBookItineraryPageMsgVisible());
    }

    @Test
    public void verifyBookItineraryPageTableVisible(){
        Assert.assertTrue(bookedItineraryPage.isItineraryTableVisible());
    }

    @Test
    public void verifyFindMyBookingViaSearchByBookingOrderId() {
        bookedItineraryPage
                .enterIdAtSearchOrderFieldAndClickGo(testedData.get(10));

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(bookedItineraryPage.isSearchResultMsgVisible());
        softAssert.assertEquals(bookedItineraryPage.getTableNumOfRows(),1);
        softAssert.assertAll();
    }

    @Test
    public void verifyDataAtTableWhenSearchByOrderId() {
        bookedItineraryPage
                .enterIdAtSearchOrderFieldAndClickGo(testedData.get(10));

        Assert.assertTrue(bookedItineraryPage.isOrderDetailsCorrect(
                0,
                testedData.get(10),
                testedData.get(1),
                testedData.get(0),
                testedData.get(3).split(" ")[0].trim(),
                TestData.get("TestData.BillingData.Valid.FirstName"),
                TestData.get("TestData.BillingData.Valid.LastName"),
                testedArrivalDate,
                testedDepartureDate,
                String.valueOf(getDifferenceBetweenTwoDatesByDays(testedArrivalDate,testedDepartureDate, Locale.UK)),
                testedData.get(2),
                testedData.get(6).split(" ")[2].trim(),
                testedData.get(9).split(" ")[2].trim()));
    }

    @Test
    public void verifyCancelTheHotelBookingByOrderId(){
        bookedItineraryPage
                .enterIdAtSearchOrderFieldAndClickGo(testedData.get(10))
                .checkSelectCancelBoxByIndex(0)
                .clickCancelSelectedButton();

 Assert.assertEquals(bookedItineraryPage.enterIdAtSearchOrderFieldAndClickGo(testedData.get(10)).getTableNumOfRows(),0);
    }

    @Test
    public void verifyCancelTheHotelBookingByTableCancelButton(){
        bookedItineraryPage
                .enterIdAtSearchOrderFieldAndClickGo(testedData.get(10))
                .clickCancelButtonByIndex(0);

        Assert.assertEquals(bookedItineraryPage.enterIdAtSearchOrderFieldAndClickGo(testedData.get(10)).getTableNumOfRows(),0);
    }

    @Test
    public void verifyCancelAllBookingItinerary(){
        bookedItineraryPage
                .checkSelectAllToCancelAtTable()
                        .clickCancelSelectedButton();

        Assert.assertEquals(bookedItineraryPage.getTableNumOfRows(),0);
    }

    @Test
    public void verifyGoingToSearchPageByClickingSearchHotelButton(){
        bookedItineraryPage
                .clickSearchHotelButton();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(),searchHotelPageURL));
    }

    @Test
    public void verifyGoingToLogoutPageAndLoggingOutByClickingLogoutButton(){
        bookedItineraryPage
                .clickLogoutButton();

        SoftAssert softAssert =new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(),logoutURL));
        getBack(getDriver());
        softAssert.assertFalse(bookedItineraryPage.staticBar.isStaticBarVisible());
        navigateToURL(getDriver(),searchHotelPageURL);
        softAssert.assertFalse(searchHotelPage.staticBar.isStaticBarVisible());
        softAssert.assertAll();
    }

    //-------------------------------------------
    @Test
    public void verifyAllBookingStillExistWhenClickCancelBookingButtonWithoutAnyBookingSelection(){
        int firstBookingNum=bookedItineraryPage
                .getTableNumOfRows();

        bookedItineraryPage
                .clickCancelSelectedButton();

        int secondBookingNum=bookedItineraryPage
                .getTableNumOfRows();

        Assert.assertEquals(secondBookingNum, firstBookingNum);
    }
}
