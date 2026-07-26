package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangePasswordPage extends BasePage {

    //<editor-fold desc="Page Objects">
    public final StaticBarAtLoggedPages staticBar;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public ChangePasswordPage(WebDriver driver) {
        super(driver);
        this.staticBar = new StaticBarAtLoggedPages(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Locators">
    private final By currentPasswordField = By.id("current_pass");
    private final By newPasswordField = By.id("new_password");
    private final By confirmPasswordField = By.id("re_password");
    private final By submitButton = By.id("Submit");
    private final By submitChangePasswordMsg = By.cssSelector("td.login_title span.reg_error");
    //</editor-fold>

    //<editor-fold desc="Actions">
    public ChangePasswordPage enterCurrentPassword(String currentPassword) {
        eActions.sendText(currentPasswordField, currentPassword);
        return this;
    }

    public ChangePasswordPage enterNewPassword(String newPassword) {
        eActions.sendText(newPasswordField, newPassword);
        return this;
    }

    public ChangePasswordPage enterConfirmPassword(String confirmPassword) {
        eActions.sendText(confirmPasswordField, confirmPassword);
        return this;
    }

    public ChangePasswordPage clickSubmitButton() {
        eActions.clickWebElement(submitButton);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getSubmitChangePasswordMsgText() {
        return eActions.getElementText(submitChangePasswordMsg);
    }
    //</editor-fold>

    //<editor-fold desc="Validations & Errors">
    public boolean isSubmitChangePasswordMsgVisible() {
        return eActions.checkElementVisibility(submitChangePasswordMsg);
    }
    //</editor-fold>

    //<editor-fold desc="Business Workflows">
    public ChangePasswordPage fillChangePasswordFormAndSubmit(String currentPass, String newPass, String confirmNewPass) {
        return enterCurrentPassword(currentPass)
                .enterNewPassword(newPass)
                .enterConfirmPassword(confirmNewPass)
                .clickSubmitButton();
    }
    //</editor-fold>
}