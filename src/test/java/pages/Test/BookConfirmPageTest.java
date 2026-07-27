package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testData.TestData;

import java.util.List;

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
                        TestData.get("TestData.BillingData.Valid.CvvNum"));
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Hotel Name Against Selected")
    public void verifyHotelNameAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getHotelNameFixedFieldText(), testedData.get(1),
                "Hotel name does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Location Against Selected")
    public void verifyLocationAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getLocationFixedFieldText(), testedData.get(0),
                "Location does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Room Type Against Selected")
    public void verifyRoomTypeAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getRoomTypeFixedFieldText(), testedData.get(2),
                "Room type does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Arrival Date Against Selected")
    public void verifyArrivalDateAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getArrivalDateFixedField(), testedArrivalDate,
                "Arrival date does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Departure Date Against Selected")
    public void verifyDepartureDateAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getDepartureDateFixedField(), testedDepartureDate,
                "Departure date does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Total Rooms Against Selected")
    public void verifyTotalRoomsAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getTotalRoomsFixedFieldText().split(" ")[0], testedData.get(3).split(" ")[0],
                "Total rooms count does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Adults Per Room Against Selected")
    public void verifyAdultsPerRoomAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getAdultsPerRoomFixedFieldText().split(" ")[0], testedData.get(4).split(" ")[0],
                "Adults per room count does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Children Per Room Against Selected")
    public void verifyChildrenPerRoomAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getChildrenPerRoomFixedFieldText().split(" ")[0], testedData.get(5).split(" ")[0],
                "Children per room count does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Price Per Night Against Selected")
    public void verifyPricePerNightAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getPricePerNightFixedFieldText().split(" ")[2], testedData.get(6).split(" ")[2],
                "Price per night does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Total Price Against Selected")
    public void verifyTotalPriceAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getTotalPriceFixedFieldText().split(" ")[2], testedData.get(7).split(" ")[2],
                "Total price does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Gst Against Selected")
    public void verifyGstAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getGstFixedFieldText().split(" ")[2], testedData.get(8).split(" ")[2],
                "GST value does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Final Billing Price Against Selected")
    public void verifyFinalBillingPriceAgainstSelected() {
        Assert.assertEquals(bookConfirmPage.getFinalBillingPriceFixedFieldText().split(" ")[2], testedData.get(9).split(" ")[2],
                "Final billing price does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify First Name Against Entered")
    public void verifyFirstNameAgainstEntered() {
        Assert.assertEquals(bookConfirmPage.getFirstNameFixedFieldText(), TestData.get("TestData.BillingData.Valid.FirstName"),
                "First name does not match the entered one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Last Name Against Entered")
    public void verifyLastNameAgainstEntered() {
        Assert.assertEquals(bookConfirmPage.getLastNameFixedFieldText(), TestData.get("TestData.BillingData.Valid.LastName"),
                "Last name does not match the entered one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Billing Address Against Entered")
    public void verifyBillingAddressAgainstEntered() {
        Assert.assertEquals(bookConfirmPage.getBillingAddressFixedFieldText(), TestData.get("TestData.BillingData.Valid.BillingAddress"),
                "Billing address does not match the entered one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Go To Search Hotel Page By Clicking On Search Hotel Button")
    public void verifyGoingToSearchHotelPageByClickingOnSearchHotelButton() {
        bookConfirmPage
                .clickSearchHotelButton();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), searchHotelPageURL),
                "Failed to navigate to Search Hotel page from Booking Confirmation page.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Go To Book Itinerary Page By Clicking On My Itinerary Button")
    public void verifyGoingToBookItineraryPageByClickingOnMyItineraryButton() {
        bookConfirmPage
                .clickMyItineraryButton();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), bookedItineraryURL),
                "Failed to navigate to Booked Itinerary page from Booking Confirmation page.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Log Out And Go To Logout Page By Clicking On Logout Button")
    public void verifyLoggingOutAndGoingToLogoutPageByClickingOnLogoutButton() {
        bookConfirmPage
                .clickLogoutButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(bookConfirmPage.staticBar.isStaticBarVisible(),
                "Static bar should not be visible after logging out.");
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), logoutURL),
                "User was not redirected to the logout URL.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Order Num Is Not Empty")
    public void verifyOrderNumNotEmpty() {
        Assert.assertFalse(bookConfirmPage.getOrderNoFixedFieldText().isEmpty(),
                "Order number field should not be empty.");
    }
}