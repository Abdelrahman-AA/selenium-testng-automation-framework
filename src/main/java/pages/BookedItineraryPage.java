package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class BookedItineraryPage extends BasePage {

    //<editor-fold desc="Page Objects">
    public final StaticBarAtLoggedPages staticBar;
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
    private final By tableCheckBoxSelectAll = By.id("check_all");

    private By getTableCheckBoxForRows(String id) {
        return By.cssSelector("input[value='" + id + "']");
    }

    private By getTableRowsCancelButton(String id) {
        return By.id("btn_id_" + id);
    }

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
        eActions.clickWebElementWithLongTimeout(searchOrderGoButton);
        return this;
    }

    public BookedItineraryPage clickCancelSelectedButton() {
        eActions.clickWebElementWithLongTimeout(cancelSelectedButton);
        driver.switchTo().alert().accept();
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
        eActions.clickWebElement(tableCheckBoxSelectAll);
        return this;
    }

    public BookedItineraryPage checkSelectCancelBoxByIndex(int index) {
        String tableOrdersValueId;
        tableOrdersValueId = getTableRowsIds().get(index);
        eActions.clickWebElement(getTableCheckBoxForRows(tableOrdersValueId));
        return this;
    }

    public BookedItineraryPage clickCancelButtonByIndex(int index) {
        String tableOrdersValueId;
        tableOrdersValueId = getTableRowsIds().get(index);
        eActions.clickWebElementWithLongTimeout(getTableRowsCancelButton(tableOrdersValueId));
        driver.switchTo().alert().accept();
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getSearchResultMsg() {
        return eActions.getElementText(searchResultMsg);
    }

    private List<String> getTableRowsIds() {
        return eActions.getRowIds(tableRowsIds);
    }

    public int getTableNumOfRows() {
        try {
            return eActions.getTableRowsCount(eActions.getTableDataAsMatrix(itineraryTable));
        } catch (Exception e) {
            return 0;
        }
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
            int rowIndex,
            String orderId,
            String hotelName,
            String location,
            String rooms,
            String firstName,
            String lastName,
            String arrivalDate,
            String departureDate,
            String numOfDays,
            String roomsType,
            String pricePerNight,
            String totalPriceInclGST) {
        List<List<String>> table = eActions.getTableDataAsMatrix(itineraryTable);

        return eActions.getTableCellValue(table, rowIndex, 1).equals(orderId)
                && eActions.getTableCellValue(table, rowIndex, 3).equals(hotelName)
                && eActions.getTableCellValue(table, rowIndex, 4).equals(location)
                && eActions.getTableCellValue(table, rowIndex, 5).split(" ")[0].trim().equals(rooms)
                && eActions.getTableCellValue(table, rowIndex, 6).equals(firstName)
                && eActions.getTableCellValue(table, rowIndex, 7).equals(lastName)
                && eActions.getTableCellValue(table, rowIndex, 8).equals(arrivalDate)
                && eActions.getTableCellValue(table, rowIndex, 9).equals(departureDate)
                && eActions.getTableCellValue(table, rowIndex, 10).split(" ")[0].trim().equals(numOfDays)
                && eActions.getTableCellValue(table, rowIndex, 11).equals(roomsType)
                && eActions.getTableCellValue(table, rowIndex, 12).split(" ")[2].trim().equals(pricePerNight)
                && eActions.getTableCellValue(table, rowIndex, 13).split(" ")[2].trim().equals(totalPriceInclGST);
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
