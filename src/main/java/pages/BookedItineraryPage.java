package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class BookedItineraryPage extends BasePage {

    //<editor-fold desc="Variables">
    private String tableOrdersValueId;
    //</editor-fold>

    //<editor-fold desc="Page Objects">
    public StaticBarAtLoggedPages staticBar;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BookedItineraryPage(WebDriver driver) {
        super(driver);
        this.staticBar = new StaticBarAtLoggedPages(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Locators">
    private final By bookItineraryPageMsg = By.xpath("//td[normalize-space()='Booked Itinerary']");
    private final By searchOrderField = By.id("order_id_text");
    private final By searchOrderGoButton = By.id("search_hotel_id");
    private final By itineraryTable = By.cssSelector("td[align='right'] table");
    private final By tableChekBoxSelectAll = By.id("check_all");
    private final By tableCheckBoxForRows = By.name("input[value='" + tableOrdersValueId + "']");
    private final By tableRowsCancelButton = By.id("btn_id_" + tableOrdersValueId);
    private final By tableRowsIds = By.xpath("//input[@name='ids[]']");
    private final By cancelSelectedButton = By.cssSelector("input[value='Cancel Selected']");
    private final By searchHotelButton = By.xpath("//input[@id='search_hotel']");
    private final By logoutButton = By.id("logout");
    private final By searchResultMsg = By.id("search_result_error");
    private final By showAllAfterSearch = By.xpath("//a[normalize-space()='Show all']");
    //</editor-fold>

    //<editor-fold desc="Actions">
    public BookedItineraryPage enterIdAtSearchOrderField(String orderId) {
        eActions.sendText(searchOrderField, orderId);
        return this;
    }

    public BookedItineraryPage clickSearchOrderGoButton() {
        eActions.clickWebElement(searchOrderGoButton);
        return this;
    }

    public BookedItineraryPage clickCancelSelectedButton() {
        eActions.clickWebElement(cancelSelectedButton);
        return this;
    }

    public SearchHotelPage clickSearchHotelButton() {
        eActions.clickWebElement(searchHotelButton);
        return new SearchHotelPage(driver);
    }

    public LogoutPage clickLogoutButton() {
        eActions.clickWebElement(logoutButton);
        return new LogoutPage(driver);
    }

    public BookedItineraryPage clickShowAllAfterSearch() {
        eActions.clickWebElement(showAllAfterSearch);
        return this;
    }

    public BookedItineraryPage checkSelectAllToCancelAtTable() {
        eActions.clickWebElement(tableChekBoxSelectAll);
        return this;
    }

    public BookedItineraryPage checkSelectCancelBoxByIndex(int index) {
        tableOrdersValueId = getTableRowsIds().get(index);
        eActions.clickWebElement(tableCheckBoxForRows);
        return this;
    }

    public BookedItineraryPage clickCancelButtonByIndex(int index) {
        tableOrdersValueId = getTableRowsIds().get(index);
        eActions.clickWebElement(tableRowsCancelButton);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getSearchResultMsg() {
        return eActions.getElementText(searchResultMsg);
    }

    public List<String> getTableRowsIds() {
        return eActions.getRowIds(tableRowsIds);
    }

    public int getTableNumOfRows() {
        return eActions.getTableRowsCount(eActions.getTableDataAsMatrix(itineraryTable));
    }
    //</editor-fold>

    //<editor-fold desc="Validations & Errors">
    public boolean isBookItineraryPageMsgVisible() {
        return eActions.checkElementVisibility(bookItineraryPageMsg);
    }

    public boolean isItineraryTableVisible() {
        return eActions.checkElementVisibility(itineraryTable);
    }

    public boolean isSearchResultMsgVisible() {
        return eActions.checkElementVisibility(searchResultMsg);
    }

    public boolean isOrderDetailsCorrect(
            int RowIndex,
            String orderId,
            String hotelName,
            String location,
            String rooms,
            String firstName,
            String lastName,
            String arrivalDate,
            String departureDate,
            String NoOfDays,
            String roomsType,
            String pricePerNight,
            String totalPriceInclGST) {
        List<List<String>> table = eActions.getTableDataAsMatrix(itineraryTable);

        return eActions.getTableCellValue(table, RowIndex, 1).equals(orderId)
                && eActions.getTableCellValue(table, RowIndex, 3).equals(hotelName)
                && eActions.getTableCellValue(table, RowIndex, 4).equals(location)
                && eActions.getTableCellValue(table, RowIndex, 5).split(" ")[0].trim().equals(rooms)
                && eActions.getTableCellValue(table, RowIndex, 6).equals(firstName)
                && eActions.getTableCellValue(table, RowIndex, 7).equals(lastName)
                && eActions.getTableCellValue(table, RowIndex, 8).equals(arrivalDate)
                && eActions.getTableCellValue(table, RowIndex, 9).equals(departureDate)
                && eActions.getTableCellValue(table, RowIndex, 10).split(" ")[0].trim().equals(NoOfDays)
                && eActions.getTableCellValue(table, RowIndex, 11).equals(roomsType)
                && eActions.getTableCellValue(table, RowIndex, 12).split(" ")[2].trim().equals(pricePerNight)
                && eActions.getTableCellValue(table, RowIndex, 13).split(" ")[2].trim().equals(totalPriceInclGST);
    }
    //</editor-fold>

    //<editor-fold desc="Business Workflows">
    public BookedItineraryPage enterIdAtSearchOrderFieldAndClickGo(String orderId) {
        return enterIdAtSearchOrderField(orderId)
                .clickSearchOrderGoButton();
    }

    public BookedItineraryPage selectOrdersToCancelAndClickCancelByIndex(List<Integer> index) {
        for (int i : index) {
            checkSelectCancelBoxByIndex(i);
        }
        clickCancelSelectedButton();
        return this;
    }
    //</editor-fold>

}
