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

        bookedItineraryPage = bookConfirmPage.staticBar.clickBookedItineraryCTA();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Book Itinerary Page Opened",
            threadPoolSize = threadPoolSize)
    public void verifyBookItineraryPageOpened(){
        Assert.assertTrue(bookedItineraryPage.isBookItineraryPageMsgVisible(),
                "Booked Itinerary page message is not visible.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Book Itinerary Page Table Visible",
            threadPoolSize = threadPoolSize)
    public void verifyBookItineraryPageTableVisible(){
        Assert.assertTrue(bookedItineraryPage.isItineraryTableVisible(),
                "Booked Itinerary table is not visible.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Find My Booking Via Search By Booking Order Id",
            threadPoolSize = threadPoolSize)
    public void verifyFindMyBookingViaSearchByBookingOrderId() {
        bookedItineraryPage
                .enterIdAtSearchOrderFieldAndClickGo(testedData.get(10));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookedItineraryPage.isSearchResultMsgVisible(),
                "Search result message should be visible.");
        softAssert.assertEquals(bookedItineraryPage.getTableNumOfRows(), 1,
                "Table row count should be exactly 1 for the searched order ID.");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Data At Table When Search By Order Id",
            threadPoolSize = threadPoolSize)
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
                String.valueOf(getDifferenceBetweenTwoDatesByDays(testedArrivalDate, testedDepartureDate, Locale.UK)),
                testedData.get(2),
                testedData.get(6).split(" ")[2].trim(),
                testedData.get(9).split(" ")[2].trim()),
                "Order details in the table do not match the expected tested data.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Cancel The Hotel Booking By Order Id",
            threadPoolSize = threadPoolSize)
    public void verifyCancelTheHotelBookingByOrderId(){
        bookedItineraryPage
                .enterIdAtSearchOrderFieldAndClickGo(testedData.get(10))
                .checkSelectCancelBoxByIndex(0)
                .clickCancelSelectedButton();

        Assert.assertEquals(bookedItineraryPage.enterIdAtSearchOrderFieldAndClickGo(testedData.get(10)).getTableNumOfRows(),
                0, "Booking was not successfully cancelled using the checkbox and cancel selected button.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Cancel The Hotel Booking By Table Cancel Button",
            threadPoolSize = threadPoolSize)
    public void verifyCancelTheHotelBookingByTableCancelButton(){
        bookedItineraryPage
                .enterIdAtSearchOrderFieldAndClickGo(testedData.get(10))
                .clickCancelButtonByIndex(0);

        Assert.assertEquals(bookedItineraryPage.enterIdAtSearchOrderFieldAndClickGo(testedData.get(10)).getTableNumOfRows(), 0,
                "Booking was not successfully cancelled using the row cancel button.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Cancel All Booking Itinerary",
            threadPoolSize = threadPoolSize)
    public void verifyCancelAllBookingItinerary(){
        bookedItineraryPage
                .checkSelectAllToCancelAtTable()
                .clickCancelSelectedButton();

        Assert.assertEquals(bookedItineraryPage.getTableNumOfRows(), 0,
                "All bookings were not successfully cancelled.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Go To Search Page By Clicking Search Hotel Button",
            threadPoolSize = threadPoolSize)
    public void verifyGoingToSearchPageByClickingSearchHotelButton(){
        bookedItineraryPage
                .clickSearchHotelButton();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), searchHotelPageURL),
                "Failed to navigate to Search Hotel page from Booked Itinerary page.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Go To Logout Page And Log Out By Clicking Logout Button",
            threadPoolSize = threadPoolSize)
    public void verifyGoingToLogoutPageAndLoggingOutByClickingLogoutButton(){
        bookedItineraryPage
                .clickLogoutButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), logoutURL),
                "User was not redirected to the logout URL.");
        getBack(getDriver());
        softAssert.assertFalse(bookedItineraryPage.staticBar.isStaticBarVisible(),
                "Static bar should not be visible after logging out.");
        navigateToURL(getDriver(), searchHotelPageURL);
        softAssert.assertFalse(searchHotelPage.staticBar.isStaticBarVisible(),
                "Static bar should not be visible when navigating back after logout.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify All Booking Still Exist When Click Cancel Booking Button Without Any Booking Selection",
            threadPoolSize = threadPoolSize)
    public void verifyAllBookingStillExistWhenClickCancelBookingButtonWithoutAnyBookingSelection(){
        int firstBookingNum = bookedItineraryPage
                .getTableNumOfRows();

        bookedItineraryPage
                .clickCancelSelectedButton();

        int secondBookingNum = bookedItineraryPage
                .getTableNumOfRows();

        Assert.assertEquals(secondBookingNum, firstBookingNum,
                "Booking count should remain unchanged when trying to cancel without selecting any booking.");
    }
}