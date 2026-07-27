package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ElementActions {

    //<editor-fold desc="Variables">
    private static final Duration defaultTimeout = Duration.ofSeconds(5);
    private static final Duration defaultTimeoutTwo = Duration.ofSeconds(60);
    //</editor-fold>

    //<editor-fold desc="Configurations">
    private final WebDriver driver;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }
    //</editor-fold>

    //<editor-fold desc="Wait Helpers (Private)">
    private WebDriverWait getWait(Duration timeout) {
        return new WebDriverWait(driver, timeout);
    }

    private WebElement getElementIfVisible(By by) {
        return getWait(defaultTimeout).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private WebElement getElementIfVisibleWithLongTimeout(By by) {
        return getWait(defaultTimeoutTwo).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private WebElement getElementIfClickable(By by) {
        return getWait(defaultTimeout).until(ExpectedConditions.elementToBeClickable(by));
    }

    private WebElement getElementIfClickableWithLongTimeout(By by) {
        return getWait(defaultTimeoutTwo).until(ExpectedConditions.elementToBeClickable(by));
    }
    //</editor-fold>

    //<editor-fold desc="Navigation Actions">
    public void getToUrl(String url) {
        driver.get(url);
    }
    //</editor-fold>

    //<editor-fold desc="Basic Element Actions (Click, Type, Get Text)">
    public void sendText(By by, CharSequence... keysToSend) {
        WebElement element = getElementIfVisible(by);
        element.clear();
        element.sendKeys(keysToSend);
    }

    public void clickWebElement(By by) {
        getElementIfClickable(by).click();
    }

    public void clickWebElementWithLongTimeout(By by) {
        getElementIfClickableWithLongTimeout(by).click();
    }

    public String getElementText(By by) {
        return getElementIfVisible(by).getText();
    }

    public String getElementDomPropertyValue(By by, String attributeName) {
        return getElementIfVisible(by).getDomProperty(attributeName);
    }
    //</editor-fold>

    //<editor-fold desc="Element State Checks (Validations)">
    public boolean checkElementVisibility(By by) {
        try {
            getElementIfVisible(by);
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean checkElementClickability(By by) {
        try {
            getElementIfClickable(by);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean checkElementSelected(By by) {
        try {
            return getElementIfVisible(by).isSelected();
        } catch (TimeoutException e) {
            return false;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Dropdown Actions">
    public int getDropDownNumOfSelections(By by) {
        return new Select(getElementIfVisible(by)).getOptions().size();
    }

    public String getDropDownTextOfSelectionByIndex(By by, int index) {
        return new Select(getElementIfVisible(by)).getOptions().get(index).getText();
    }

    public String getDropDownSelectedOptionText(By by) {
        return new Select(getElementIfVisible(by)).getFirstSelectedOption().getText();
    }

    public void dropDownSelectByIndex(By by, int index) {
        new Select(getElementIfVisible(by)).selectByIndex(index);
    }

    public String getDropDownDefaultOptionText(By by) {
        return new Select(getElementIfVisible(by)).getFirstSelectedOption().getText();
    }
    //</editor-fold>

    //<editor-fold desc="Multiple Elements & Lists">
    public void clickElementFromRadioByIndex(By by, int index) {
        List<WebElement> elements = getWait(defaultTimeout)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        if (index >= 0 && index < elements.size()) {
            getWait(defaultTimeout).until(ExpectedConditions.elementToBeClickable(elements.get(index))).click();
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds. Total elements found: " + elements.size());
        }
    }
    //</editor-fold>

    //<editor-fold desc="Table Actions">
    public List<List<String>> getTableDataAsMatrix(By tableLocator) {
        List<List<String>> tableData = new ArrayList<>();
        WebElement table = getElementIfVisibleWithLongTimeout(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            List<String> rowData = new ArrayList<>();

            for (WebElement cell : cells) {
                List<WebElement> inputs = cell.findElements(By.tagName("input"));
                if (!inputs.isEmpty()) {
                    rowData.add(inputs.get(0).getDomProperty("value"));
                } else {
                    rowData.add(cell.getText().trim());
                }
            }
            if (!rowData.isEmpty()) {
                tableData.add(rowData);
            }
        }
        return tableData;
    }

    public int getTableRowsCount(List<List<String>> tableMatrix) {
        return tableMatrix.size();
    }

    public String getTableCellValue(List<List<String>> tableMatrix, int rowIndex, int columnIndex) {
        return tableMatrix.get(rowIndex).get(columnIndex);
    }

    public List<String> getRowIds(By tableIdHoldersLocator) {
        List < String > idsList = new ArrayList<>();

        if(getElementIfVisibleWithLongTimeout(tableIdHoldersLocator).isDisplayed()){
        List<WebElement> checkboxes =driver.findElements(tableIdHoldersLocator);
        for (WebElement checkbox : checkboxes) {
            idsList.add(checkbox.getAttribute("value"));
        }}
        return idsList;
    }
    //</editor-fold>

    //<editor-fold desc="Special / Custom Actions">
    public void solveCaptchaTextManually(By successElementLocator, int timeoutInSeconds) {
        getWait(Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(successElementLocator));
        System.out.println("CAPTCHA BYPASSED MANUALLY");
    }
    //</editor-fold>
}