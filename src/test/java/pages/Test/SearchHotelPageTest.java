package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testData.TestData;

import java.util.Locale;

import static utils.helpers.Helpers.*;

public class SearchHotelPageTest extends BaseTest {

    private int indexOfLastOptionOfLocation;
    private int indexOfLastOptionOfHotels;
    private int indexOfLastOptionOfRoomTypes;
    private int indexOfLastOptionOfNumsOfRooms;
    private int indexOfLastOptionOfAdultsPerRoom;
    private int indexOfLastOptionOfChildrenPerRoom;

    private final String passedDate = TestData.get("TestData.Date.PastDate");
    private final String textNotDate = TestData.get("TestData.Date.TextNotDate");


    @BeforeMethod
    public void initializePage() {
        searchHotelPage = homePage
                .openHomePageAndLoginWithValidRegisteredCredentials(registeredUserName, registeredPassword, homePageURL);

        indexOfLastOptionOfLocation = searchHotelPage.getNumOfLocations() - 1;
        indexOfLastOptionOfHotels = searchHotelPage.getNumOfHotels() - 1;
        indexOfLastOptionOfRoomTypes = searchHotelPage.getNumOfRoomTypes() - 1;
        indexOfLastOptionOfNumsOfRooms = searchHotelPage.getNumOfNumsOfRooms() - 1;
        indexOfLastOptionOfAdultsPerRoom = searchHotelPage.getNumOfAdultsPerRoom() - 1;
        indexOfLastOptionOfChildrenPerRoom = searchHotelPage.getNumOfChildrenPerRoom() - 1;
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Go To Search Hotel Page",
            threadPoolSize = threadPoolSize)
    public void verifyGoingToSearchHotelPage() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), searchHotelPageURL),
                "Current URL does not match the expected Search Hotel Page URL!");
        softAssert.assertTrue(searchHotelPage.isSearchHotelPageMsgVisible(),
                "Search Hotel Page header message is not visible!");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Select Last Option From Location DropDown List",
            threadPoolSize = threadPoolSize)
    public void verifySelectingLastOptionOfLocationDropDown() {
        String expectedLocation = searchHotelPage.getLocationNameByIndex(indexOfLastOptionOfLocation);

        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation);

        Assert.assertTrue(searchHotelPage.isDropDownLocationSelected(expectedLocation),
                "Failed to select the last option '" + expectedLocation + "' from Location dropdown!");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Select Last Option From Hotels DropDown List",
            threadPoolSize = threadPoolSize)
    public void verifySelectingLastOptionOfHotelsDropDown() {
        String expectedHotel = searchHotelPage.getHotelNameByIndex(indexOfLastOptionOfHotels);

        searchHotelPage
                .selectIndexOfHotel(indexOfLastOptionOfHotels);

        Assert.assertTrue(searchHotelPage.isDropDownHotelSelected(expectedHotel),
                "Failed to select the last option '" + expectedHotel + "' from Hotels dropdown!");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Select Last Option From Room Type DropDown List",
            threadPoolSize = threadPoolSize)
    public void verifySelectingLastOptionOfRoomTypeDropDown() {
        String expectedRoomType = searchHotelPage.getRoomTypeNameByIndex(indexOfLastOptionOfRoomTypes);

        searchHotelPage
                .selectIndexOfRoomType(indexOfLastOptionOfRoomTypes);

        Assert.assertTrue(searchHotelPage.isDropDownRoomTypeSelected(expectedRoomType),
                "Failed to select the last option '" + expectedRoomType + "' from Room Type dropdown!");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Select Last Option From Number Of Rooms DropDown List",
            threadPoolSize = threadPoolSize)
    public void verifySelectingLastOptionOfNumOfRoomsDropDown() {
        String expectedRooms = searchHotelPage.getNumOfRoomNameByIndex(indexOfLastOptionOfNumsOfRooms);

        searchHotelPage
                .selectIndexOfNumsOfRoom(indexOfLastOptionOfNumsOfRooms);

        Assert.assertTrue(searchHotelPage.isDropDownNumbersOfRoomsSelected(expectedRooms),
                "Failed to select the last option '" + expectedRooms + "' from Number of Rooms dropdown!");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Select Last Option From Adults Per Room DropDown List",
            threadPoolSize = threadPoolSize)
    public void verifySelectingLastOptionOfAdultsPerRoomsDropDown() {
        String expectedAdults = searchHotelPage.getAdultsPerRoomNameByIndex(indexOfLastOptionOfAdultsPerRoom);

        searchHotelPage
                .selectIndexOfAdultsPerRoom(indexOfLastOptionOfAdultsPerRoom);

        Assert.assertTrue(searchHotelPage.isDropDownAdultPerRoomSelected(expectedAdults),
                "Failed to select the last option '" + expectedAdults + "' from Adults Per Room dropdown!");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Select Last Option From Children Per Room DropDown List",
            threadPoolSize = threadPoolSize)
    public void verifySelectingLastOptionOfChildrenPerRoomsDropDown() {
        String expectedChildren = searchHotelPage.getChildrenPerRoomNameByIndex(indexOfLastOptionOfChildrenPerRoom);

        searchHotelPage
                .selectIndexOfChildrenPerRoom(indexOfLastOptionOfChildrenPerRoom);

        Assert.assertTrue(searchHotelPage.isDropDownChildrenPerRoomSelected(expectedChildren),
                "Failed to select the last option '" + expectedChildren + "' from Children Per Room dropdown!");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Empty All Fields When Click Reset Button",
            threadPoolSize = threadPoolSize)
    public void verifyResetAllFieldsWhenClickReset() {
        String defaultLocation = searchHotelPage.getDefaultOfLocations();
        String defaultHotel = searchHotelPage.geDefaultOfHotels();
        String defaultRoomTypes = searchHotelPage.getDefaultOfRoomTypes();
        String defaultNumsOfRooms = searchHotelPage.getDefaultOfNumsOfRooms();
        String defaultAdultsPerRoom = searchHotelPage.getDefaultOfAdultsPerRoom();
        String defaultChildrenPerRoom = searchHotelPage.getDefaultOfChildrenPerRoom();

        searchHotelPage
                .selectAndFillAllFields(
                        indexOfLastOptionOfLocation,
                        indexOfLastOptionOfHotels,
                        indexOfLastOptionOfRoomTypes,
                        indexOfLastOptionOfNumsOfRooms,
                        getDateOffsetFromToday(1, Locale.UK),
                        getDateOffsetFromToday(2, Locale.UK),
                        indexOfLastOptionOfAdultsPerRoom,
                        indexOfLastOptionOfChildrenPerRoom)
                .clickReset();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(searchHotelPage.isDropDownLocationSelected(defaultLocation),
                "Location dropdown did not reset to default value!");
        softAssert.assertTrue(searchHotelPage.isDropDownHotelSelected(defaultHotel),
                "Hotel dropdown did not reset to default value!");
        softAssert.assertTrue(searchHotelPage.isDropDownRoomTypeSelected(defaultRoomTypes),
                "Room Type dropdown did not reset to default value!");
        softAssert.assertTrue(searchHotelPage.isDropDownNumbersOfRoomsSelected(defaultNumsOfRooms),
                "Number of Rooms dropdown did not reset to default value!");
        softAssert.assertEquals(searchHotelPage.getCurrentCheckInFieldText(), getTodayDate(Locale.UK),
                "Check-in field did not reset to Today's date!");
        softAssert.assertEquals(searchHotelPage.getCurrentCheckOutFieldText(), getDateOffsetFromToday(1, Locale.UK),
                "Check-out field did not reset to Tomorrow's date!");
        softAssert.assertTrue(searchHotelPage.isDropDownAdultPerRoomSelected(defaultAdultsPerRoom),
                "Adults per room dropdown did not reset to default value!");
        softAssert.assertTrue(searchHotelPage.isDropDownChildrenPerRoomSelected(defaultChildrenPerRoom),
                "Children per room dropdown did not reset to default value!");
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), searchHotelPageURL),
                "Search Hotel Page URL Is Not The Current URL");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Going To Hotel Reservation Options When Submit Valid Data",
            threadPoolSize = threadPoolSize)
    public void verifyGoingToHotelReservationOptionsWhenValidData() {
        selectHotelPage = searchHotelPage
                .selectAndFillAllFieldsAndClickSearch(
                        indexOfLastOptionOfLocation,
                        indexOfLastOptionOfHotels,
                        indexOfLastOptionOfRoomTypes,
                        indexOfLastOptionOfNumsOfRooms,
                        getDateOffsetFromToday(1, Locale.UK),
                        getDateOffsetFromToday(2, Locale.UK),
                        indexOfLastOptionOfAdultsPerRoom,
                        indexOfLastOptionOfChildrenPerRoom);

        Assert.assertTrue(selectHotelPage.isSearchResultsTableVisible(),
                "Search results table is not displayed after performing search with valid data!");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With No Selected Location",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForNoSelectedLocation() {
        searchHotelPage
                .clickSearch();

        Assert.assertTrue(searchHotelPage.isLocationErrorMsgVisible(),
                "Location error message is not visible when searching without selecting a location!");
        Assert.assertEquals(searchHotelPage.getLocationErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.LocationNotSelected"),
                "Location error message text does not match expected value!");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With Empty Check In Date",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForEmptyCheckInDate() {
        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation)
                .enterCheckInDate(empty)
                .clickSearch();

        Assert.assertTrue(searchHotelPage.isCheckInDateErrorMsgVisible(),
                "Check-in date error message is not visible for empty check-in date!");
        Assert.assertEquals(searchHotelPage.getCheckInDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.EmptyCheckInDate"),
                "Empty check-in date error message text mismatch!");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With Passed Check In Date",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForPassedCheckInDate() {
        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation)
                .enterCheckInDate(passedDate)
                .clickSearch();

        Assert.assertTrue(searchHotelPage.isCheckInDateErrorMsgVisible(),
                "Check-in date error message is not visible for a past date!");
        Assert.assertEquals(searchHotelPage.getCheckInDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.PassedDate"),
                "Past check-in date error message text mismatch!");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With Empty Check Out Date",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForEmptyCheckOutDate() {
        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation)
                .enterCheckOutDate(empty)
                .clickSearch();

        Assert.assertTrue(searchHotelPage.isCheckOutDateErrorMsgVisible(),
                "Check-out date error message is not visible for empty check-out date!");
        Assert.assertEquals(searchHotelPage.getCheckOutDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.EmptyCheckOutDate"),
                "Empty check-out date error message text mismatch!");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With Passed Check Out Date",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForPassedCheckOutDate() {
        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation)
                .enterCheckInDate(passedDate)
                .clickSearch();

        Assert.assertTrue(searchHotelPage.isCheckOutDateErrorMsgVisible(),
                "Check-out date error message is not visible for a past date!");
        Assert.assertEquals(searchHotelPage.getCheckOutDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.PassedDate"),
                "Past check-out date error message text mismatch!");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With Check Out Date Is Before Check In Date Case If Today And Yesterday",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForCheckOutDateIsBeforeCheckInDateCaseIfTodayAndYesterday() {
        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation)
                .enterCheckInDate(getTodayDate(Locale.UK))
                .enterCheckOutDate(getDateOffsetFromToday(-1, Locale.UK))
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(searchHotelPage.isCheckInDateErrorMsgVisible(),
                "Check-in date error message should NOT be visible when Check-in is today!");
        softAssert.assertTrue(searchHotelPage.isCheckOutDateErrorMsgVisible(),
                "Check-out date error message should be visible when Check-out is yesterday!");
        softAssert.assertEquals(searchHotelPage.getCheckOutDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.PassedDate"),
                "Check-out error message text mismatch for yesterday's date!");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With Check Out Date Is Before Check In Date Case If Today And Tomorrow",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForCheckOutDateIsBeforeCheckInDateCaseIfTodayAndTomorrow() {
        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation)
                .enterCheckInDate(getDateOffsetFromToday(1, Locale.UK))
                .enterCheckOutDate(getTodayDate(Locale.UK))
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(searchHotelPage.isCheckInDateErrorMsgVisible(),
                "Check-in date error message should be visible when Check-in is after Check-out!");
        softAssert.assertEquals(searchHotelPage.getCheckInDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.CheckInDateAfterCheckOutDate"),
                "Check-in date error message text mismatch!");
        softAssert.assertTrue(searchHotelPage.isCheckOutDateErrorMsgVisible(),
                "Check-out date error message should be visible when Check-out is before Check-in!");
        softAssert.assertEquals(searchHotelPage.getCheckOutDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.CheckOutDateBeforeCheckInDate"),
                "Check-out date error message text mismatch!");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With Check Out Date Is Before Check In Date Case If Tomorrow And After Tomorrow",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForCheckOutDateIsBeforeCheckInDateCaseIfTomorrowAndAfterTomorrow() {
        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation)
                .enterCheckInDate(getDateOffsetFromToday(2, Locale.UK))
                .enterCheckOutDate(getDateOffsetFromToday(1, Locale.UK))
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(searchHotelPage.isCheckInDateErrorMsgVisible(),
                "Check-in date error message is missing when Check-in is Day 2 and Check-out is Day 1!");
        softAssert.assertEquals(searchHotelPage.getCheckInDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.CheckInDateAfterCheckOutDate"),
                "Check-in date error text mismatch!");
        softAssert.assertTrue(searchHotelPage.isCheckOutDateErrorMsgVisible(),
                "Check-out date error message is missing when Check-out is Day 1 and Check-in is Day 2!");
        softAssert.assertEquals(searchHotelPage.getCheckOutDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.CheckOutDateBeforeCheckInDate"),
                "Check-out date error text mismatch!");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With Check In And Out Date For Wrong Format",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForCheckInAndOutDateForWrongFormat() {
        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation)
                .enterCheckInDate(getDateOffsetFromToday(1, Locale.US))
                .enterCheckOutDate(getDateOffsetFromToday(2, Locale.US))
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(searchHotelPage.isCheckInDateErrorMsgVisible(),
                "Check-in error message is missing for invalid date format (US Locale)!");
        softAssert.assertEquals(searchHotelPage.getCheckInDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.WrongDateFormat"),
                "Wrong format Check-in date error message mismatch!");
        softAssert.assertTrue(searchHotelPage.isCheckOutDateErrorMsgVisible(),
                "Check-out error message is missing for invalid date format (US Locale)!");
        softAssert.assertEquals(searchHotelPage.getCheckOutDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.WrongDateFormat"),
                "Wrong format Check-out date error message mismatch!");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try Search With Check In And Out Date Are Text Not Date",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForCheckInAndOutDateTextNotDate() {
        searchHotelPage
                .selectIndexOfLocation(indexOfLastOptionOfLocation)
                .enterCheckInDate(textNotDate)
                .enterCheckOutDate(textNotDate)
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(searchHotelPage.isCheckInDateErrorMsgVisible(),
                "Check-in date error message is missing when text is passed instead of date!");
        softAssert.assertEquals(searchHotelPage.getCheckInDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.WrongDateFormat"),
                "Non-date text Check-in error message mismatch!");
        softAssert.assertTrue(searchHotelPage.isCheckOutDateErrorMsgVisible(),
                "Check-out date error message is missing when text is passed instead of date!");
        softAssert.assertEquals(searchHotelPage.getCheckOutDateErrorMsgText(), TestData.get("Pages.SearchHotelPage.Errors.WrongDateFormat"),
                "Non-date text Check-out error message mismatch!");
        softAssert.assertAll();
    }
}