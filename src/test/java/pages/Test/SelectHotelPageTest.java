package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testData.TestData;

import java.util.List;
import java.util.Locale;

import static utils.helpers.Helpers.*;

public class SelectHotelPageTest extends BaseTest {

    private List<String> testedData;
    private String testedArrivalDate = getTodayDate(Locale.UK);
    private String testedDepartureDate = getDateOffsetFromToday(1, Locale.UK);

    @BeforeMethod
    public void initializePage() {
        searchHotelPage = homePage
                .openHomePageAndLoginWithValidRegisteredCredentials(registeredUserName, registeredPassword, homePageURL);
        testedData = searchHotelPage.getTestedDataForSave(searchHotelPage.getNumOfLocations() - 1,
                searchHotelPage.getNumOfHotels() - 1,
                searchHotelPage.getNumOfRoomTypes() - 1,
                searchHotelPage.getNumOfNumsOfRooms() - 1,
                searchHotelPage.getNumOfAdultsPerRoom() - 1,
                searchHotelPage.getNumOfChildrenPerRoom() - 1
        );
        selectHotelPage = searchHotelPage
                .selectAndFillAllFieldsAndClickSearch(
                        searchHotelPage.getNumOfLocations() - 1,
                        searchHotelPage.getNumOfHotels() - 1,
                        searchHotelPage.getNumOfRoomTypes() - 1,
                        searchHotelPage.getNumOfNumsOfRooms() - 1,
                        testedArrivalDate,
                        testedDepartureDate,
                        searchHotelPage.getNumOfAdultsPerRoom() - 1,
                        searchHotelPage.getNumOfChildrenPerRoom() - 1);
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Result Table Be Visible",
            threadPoolSize = threadPoolSize)
    public void verifyTableOfHotelsIsVisible() {
        Assert.assertTrue(selectHotelPage.isSearchResultsTableVisible());
    }


    @Test(groups = {"smoke", "happy-path"},
            description = "Should Result Data At Table Be The Same Of Enterd Data At Search",
            threadPoolSize = threadPoolSize)
    public void verifyTableOfOptionsAgainstSearchedData() {
        Assert.assertTrue(selectHotelPage.isTableDataReturnedTrue(testedData, testedArrivalDate, testedDepartureDate),
                "Returned table data does not match the search parameters!");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Return To Search Form When Click Cancel After Search",
            threadPoolSize = threadPoolSize)
    public void verifyReturnToSearchFormWhenClickCancel() {
        searchHotelPage = selectHotelPage
                .clickCancel();

        Assert.assertTrue(searchHotelPage.isSearchHotelPageMsgVisible(),
                "Page header is not visible after clicking Cancel!");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Go To Book Hotel Page When Click Continue After Select Hotel",
            threadPoolSize = threadPoolSize)
    public void verifyGoingToBookHotelPageAfterSelectHotelAndClickContinue() {
        selectHotelPage
                .selectRadioButtonByIndex(selectHotelPage.getResultsTableRowCount() - 1)
                .clickContinue();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), bookHotelPageURL),
                "User was not redirected to Book Hotel Page after selecting a hotel and clicking Continue!");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Click Continue Without Select Hotel",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgWhenClickContinueWithoutSelectHotel() {
        selectHotelPage
                .clickContinue();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), searchHotelPageURL),
                "User navigated away from Search Hotel page even without selecting a radio button!");
        Assert.assertTrue(selectHotelPage.isContinueErrorMsgVisible(),
                "Error message is not displayed when clicking Continue without choosing a hotel!");
        Assert.assertEquals(selectHotelPage.getContinueErrorMsgText(), TestData.get("Pages.SelectHotelPage.Errors.NoSelectedHotel"),
                "No selected hotel error message text mismatch!");
    }
}
