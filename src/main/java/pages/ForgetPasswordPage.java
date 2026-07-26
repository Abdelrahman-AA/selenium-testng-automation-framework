package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgetPasswordPage extends BasePage {

    //<editor-fold desc="Variables">
    private static final String valueAttribute = "value";
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public ForgetPasswordPage(WebDriver driver) {
        super(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Locators">
    private final By forgetPasswordPageMsg = By.xpath("//td[normalize-space()='Forgot Password Form']");
    private final By backToLoginPageCta = By.xpath("//a[normalize-space()='Go back to Login page']");
    private final By emailField = By.id("emailadd_recovery");
    private final By emailPasswordButton = By.id("Submit");
    private final By resetButton = By.id("Reset");
    private final By afterEmailPasswordBackToLOginPage=By.cssSelector("a[href='index.php']");

    private final By successfullyEmailedPasswordMsg = By.cssSelector(".reg_success");
    private final By emailFieldErrorMsg = By.id("emailadd_span");
    //</editor-fold>

    //<editor-fold desc="Actions">
    public ForgetPasswordPage enterEmailField(String email) {
        eActions.sendText(emailField, email);
        return this;
    }

    public HomePage clickBackToLoginPageCta() {
        eActions.clickWebElement(backToLoginPageCta);
        return new HomePage(driver);
    }

    public ForgetPasswordPage clickEmailPasswordButtonCta() {
        eActions.clickWebElement(emailPasswordButton);
        return this;
    }

    public ForgetPasswordPage clickResetButtonCta() {
        eActions.clickWebElement(resetButton);
        return this;
    }

    public HomePage clickBackToLoginPageAfterEmailPassword(){
        eActions.clickWebElement(afterEmailPasswordBackToLOginPage);
        return new HomePage(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getEmailFieldErrorMsg() {
        return eActions.getElementText(emailFieldErrorMsg);
    }

    public String getEmailFieldText() {
        return eActions.getElementDomPropertyValue(emailField, valueAttribute);
    }
    //</editor-fold>

    //<editor-fold desc="Validations & Errors">
    public boolean isForgetPasswordPageMsgVisible() {
        return eActions.checkElementVisibility(forgetPasswordPageMsg);
    }

    public boolean isSuccessfullyEmailedPasswordMsgVisible() {
        return eActions.checkElementVisibility(successfullyEmailedPasswordMsg);
    }

    public boolean isEmailFieldErrorMsgVisible() {
        return eActions.checkElementVisibility(emailFieldErrorMsg);
    }
    //</editor-fold>

    //<editor-fold desc="Business Workflows">
    public ForgetPasswordPage enterEmailAndSubmit(String email){
        return enterEmailField(email)
                .clickEmailPasswordButtonCta();
    }
    //</editor-fold>
}
