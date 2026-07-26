package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogoutPage extends BasePage {

    //<editor-fold desc="Constructor">
    public LogoutPage(WebDriver driver) {
        super(driver);
    }
    //</editor-fold>


    //<editor-fold desc="Locators">
    private final By successfullyLoggedOutMsg = By.cssSelector("td.reg_success");
    private final By clickHereToLoginAgainCTA = By.xpath("//a[normalize-space()='Click here to login again']");
    //</editor-fold>


    //<editor-fold desc="Actions">
    public HomePage clickToLoginAgainCTA() {
        eActions.clickWebElement(clickHereToLoginAgainCTA);
        return new HomePage(driver);
    }
    //</editor-fold>


    //<editor-fold desc="Validations & Errors">
    public boolean isSuccessfullyLoggedOutMsgVisible() {
        return eActions.checkElementVisibility(successfullyLoggedOutMsg);
    }
    //</editor-fold>
}