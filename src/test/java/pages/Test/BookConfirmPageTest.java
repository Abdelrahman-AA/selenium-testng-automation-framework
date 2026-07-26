package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testData.TestData;

import java.util.List;
import java.util.Locale;

import static utils.helpers.Helpers.*;

public class BookConfirmPageTest extends BaseTest {

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
    }

    @Test
    public void verifyHotelNameAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getHotelNameFixedFieldText(),testedData.get(1));
    }

    @Test
    public void verifyLocationAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getLocationFixedFieldText(),testedData.get(0));
    }

    @Test
    public void verifyRoomTypeAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getRoomTypeFixedFieldText(),testedData.get(2));
    }

    @Test
    public void verifyArrivalDateAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getArrivalDateFixedField(),testedArrivalDate);
    }

    @Test
    public void verifyDepartureDateAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getDepartureDateFixedField(),testedDepartureDate);
    }

    @Test
    public void verifyTotalRoomsAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getTotalRoomsFixedFieldText().split(" ")[0],testedData.get(3).split(" ")[0]);
    }

    @Test
    public void verifyAdultsPerRoomAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getAdultsPerRoomFixedFieldText().split(" ")[0],testedData.get(4).split(" ")[0]);
    }

    @Test
    public void verifyChildrenPerRoomAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getChildrenPerRoomFixedFieldText().split(" ")[0],testedData.get(5).split(" ")[0]);
    }

    @Test
    public void verifyPricePerNightAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getPricePerNightFixedFieldText().split(" ")[2],testedData.get(6).split(" ")[2]);
    }

    @Test
    public void verifyTotalPriceAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getTotalPriceFixedFieldText().split(" ")[2],testedData.get(7).split(" ")[2]);
    }

    @Test
    public void verifyGstAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getGstFixedFieldText().split(" ")[2],testedData.get(8).split(" ")[2]);
    }

    @Test
    public void verifyFinalBillingPriceAgainstSelected(){
        Assert.assertEquals(bookConfirmPage.getFinalBillingPriceFixedFieldText().split(" ")[2],testedData.get(9).split(" ")[2]);
    }

    @Test
    public void verifyFirstNameAgainstEntered(){
        Assert.assertEquals(bookConfirmPage.getFirstNameFixedFieldText(),TestData.get("TestData.BillingData.Valid.FirstName"));
    }

    @Test
    public void verifyLastNameAgainstEntered(){
        Assert.assertEquals(bookConfirmPage.getLastNameFixedFieldText(),TestData.get("TestData.BillingData.Valid.LastName"));
    }

    @Test
    public void verifyBillingAddressAgainstEntered(){
        Assert.assertEquals(bookConfirmPage.getBillingAddressFixedFieldText(),TestData.get("TestData.BillingData.Valid.BillingAddress"));
    }

    @Test
    public void verifyGoingToSearchHotelPageByClickingOnSearchHotelButton(){
        bookConfirmPage
                .clickSearchHotelButton();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(),searchHotelPageURL));
    }

    @Test
    public void verifyGoingToBookItineraryPageByClickingOnMyItineraryButton(){
        bookConfirmPage
                .clickMyItineraryButton();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(),bookedItineraryURL));
    }

    @Test
    public void verifyLoggingOutAndGoingToLogoutPageByClickingOnLogoutButton(){
        bookConfirmPage
                .clickLogoutButton();

        SoftAssert softAssert =new SoftAssert();
        softAssert.assertFalse(bookConfirmPage.staticBar.isStaticBarVisible());
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(),logoutURL));
        softAssert.assertAll();
    }
//--------------------------------------------------------------------
    @Test
    public void verifyOrderNumNotEmpty(){
        Assert.assertFalse(bookConfirmPage.getOrderNoFixedFieldText().isEmpty());
    }
}
