package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectHotelPage extends BasePage {

    //<editor-fold desc="Page Objects">
    public final StaticBarAtLoggedPages staticBar;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public SelectHotelPage(WebDriver driver) {
        super(driver);
        this.staticBar = new StaticBarAtLoggedPages(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Locators">
    private final By resultsTable = By.xpath("//td[@align='right']//table");
    private final By tableRadio = By.xpath(".//input[@type='radio']");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");

    private final By continueErrorMsg = By.id("radiobutton_span");
    //</editor-fold>

    //<editor-fold desc="Actions">
    public BookHotelPage clickContinue() {
        eActions.clickWebElement(continueButton);
        return new BookHotelPage(driver);
    }

    public SearchHotelPage clickCancel() {
        eActions.clickWebElement(cancelButton);
        return new SearchHotelPage(driver);
    }

    public SelectHotelPage selectRadioButtonByIndex(int index) {
        eActions.clickElementFromRadioByIndex(tableRadio, index);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getContinueErrorMsgText() {
        return eActions.getElementText(continueErrorMsg);
    }

    public int getNumsOfRadioOptions() {
        return getResultsTableRowCount();
    }

    public List<List<String>> getResultsTableMatrix() {
        return eActions.getTableDataAsMatrix(resultsTable);
    }

    public int getResultsTableRowCount() {
        return eActions.getTableRowsCount(getResultsTableMatrix());
    }

    public String getTableCellValueByRowAndColumn(int rowIndex, int columnIndex) {
        return eActions.getTableCellValue(getResultsTableMatrix(), rowIndex, columnIndex);
    }

    public List<String> getColumnDataByIndex(int columnIndex) {
        return getColumnFromMatrix(getResultsTableMatrix(), columnIndex);
    }

    private List<String> getColumnFromMatrix(List<List<String>> matrix, int columnIndex) {
        List<String> columnValues = new ArrayList<>();
        for (List<String> row : matrix) {
            if (columnIndex < row.size()) {
                columnValues.add(row.get(columnIndex));
            }
        }
        return columnValues;
    }

    public SelectHotelPage getTableDataLists(List<String> hotelName, List<String> location, List<String> rooms,
                                             List<String> arrivalDate, List<String> departureDate, List<String> noOfDays,
                                             List<String> roomsType, List<String> pricePerNight, List<String> totalPriceExclGST) {
        List<List<String>> matrix = getResultsTableMatrix();

        hotelName.clear();
        hotelName.addAll(getColumnFromMatrix(matrix, 1));
        location.clear();
        location.addAll(getColumnFromMatrix(matrix, 2));
        rooms.clear();
        rooms.addAll(getColumnFromMatrix(matrix, 3));
        arrivalDate.clear();
        arrivalDate.addAll(getColumnFromMatrix(matrix, 4));
        departureDate.clear();
        departureDate.addAll(getColumnFromMatrix(matrix, 5));
        noOfDays.clear();
        noOfDays.addAll(getColumnFromMatrix(matrix, 6));
        roomsType.clear();
        roomsType.addAll(getColumnFromMatrix(matrix, 7));
        pricePerNight.clear();
        pricePerNight.addAll(getColumnFromMatrix(matrix, 8));
        totalPriceExclGST.clear();
        totalPriceExclGST.addAll(getColumnFromMatrix(matrix, 9));

        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Validations & Errors">
    public boolean isContinueErrorMsgVisible() {
        return eActions.checkElementVisibility(continueErrorMsg);
    }

    public boolean isSearchResultsTableVisible() {
        return eActions.checkElementVisibility(resultsTable);
    }


    public boolean isTableDataReturnedTrue(List<String> testedData, String arrivalDate, String departureDate) {
        List<List<String>> matrix = getResultsTableMatrix();
        String expectedRoomNum = testedData.get(3).split("-")[0].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.UK);
        long numOfDays = ChronoUnit.DAYS.between(LocalDate.parse(arrivalDate, formatter), LocalDate.parse(departureDate, formatter));
        boolean isLocationValid = getColumnFromMatrix(matrix, 2).stream().allMatch(val -> val.trim().equalsIgnoreCase(testedData.get(0).trim()));
        boolean isRoomsValid = getColumnFromMatrix(matrix, 3).stream().allMatch(val -> val.contains(expectedRoomNum));
        boolean isArrivalValid = getColumnFromMatrix(matrix, 4).stream().allMatch(val -> val.trim().equals(arrivalDate.trim()));
        boolean isDepartureValid = getColumnFromMatrix(matrix, 5).stream().allMatch(val -> val.trim().equals(departureDate.trim()));
        boolean isNoOfDaysValid = getColumnFromMatrix(matrix, 6).stream().allMatch(val -> val.contains(String.valueOf(numOfDays)));
        boolean isRoomsTypeValid = getColumnFromMatrix(matrix, 7).stream().allMatch(val -> val.trim().equalsIgnoreCase(testedData.get(2).trim()));

        return isLocationValid && isRoomsValid && isArrivalValid && isDepartureValid && isNoOfDaysValid && isRoomsTypeValid;
    }
    //</editor-fold>

    //<editor-fold desc="Business Workflows">
    public BookHotelPage selectHotelFromTableAndClickContinueByIndex(int index) {
        return selectRadioButtonByIndex(index)
                .clickContinue();
    }
    //</editor-fold>
}
