package pages.Test;

import org.testng.annotations.BeforeMethod;
import testData.TestData;

import java.util.List;
import java.util.Locale;

import static utils.helpers.Helpers.getDateOffsetFromToday;
import static utils.helpers.Helpers.getTodayDate;

public class BookConfirmPageTest extends BaseTest {

    private List<String> testedData;
    private String testedArrivalDate = getTodayDate(Locale.UK);
    private String testedDepartureDate = getDateOffsetFromToday(1, Locale.UK);

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

        bookConfirmPage = selectHotelPage
                .selectHotelFromTableAndClickContinueByIndex(tableRowSelection)
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
}
