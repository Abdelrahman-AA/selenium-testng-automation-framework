package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    //<editor-fold desc="Variables">
    private static final String VALUE_ATTRIBUTE = "value";
    //</editor-fold>


    //<editor-fold desc="Constructor">
    public RegisterPage(WebDriver driver) {
        super(driver);
    }
    //</editor-fold>


    //<editor-fold desc="Locators">
    private final By backToLoginPageCTA = By.xpath("//a[normalize-space()='Go back to Login page']");
    private final By userNameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By confirmPasswordField = By.id("re_password");
    private final By fullNameField = By.id("full_name");
    private final By emailField = By.id("email_add");
    private final By captchaImage = By.id("captcha");
    private final By refreshCaptchaImage = By.xpath("//img[@alt='Refresh Captcha']");
    private final By captchaTextField = By.id("captcha-form");
    private final By termsAndConditionsCTA = By.xpath("//a[normalize-space()='Terms & Conditions']");
    private final By checkAgreementTermsAndConditions = By.id("tnc_box");
    private final By registerButton = By.id("Submit");
    private final By resetButton = By.id("Reset");
    private final By afterRegisterBackToLoginPage=By.cssSelector("a[href='index.php']");

    private final By errorMsgUserName = By.id("username_span");
    private final By errorMsgPassword = By.id("password_span");
    private final By errorMsgConfirmPassword = By.id("re_password_span");
    private final By errorMsgFullName = By.id("full_name_span");
    private final By errorMsgEmail = By.id("email_add_span");
    private final By errorMsgCaptcha = By.id("captcha_span");
    private final By errorMsgMustAgreeTerms = By.id("tnc_span");
    private final By successfullyRegistrationMsg = By.xpath("/html/body/table[2]/tbody/tr/td[1]/table/tbody/tr/td");
    //</editor-fold>


    //<editor-fold desc="Actions">
    public RegisterPage openRegisterPageURL(String registerPageURL) {
        eActions.getToUrl(registerPageURL);
        return this;
    }

    public HomePage clickBackToLoginPageCTA() {
        eActions.clickWebElement(backToLoginPageCTA);
        return new HomePage(driver);
    }

    public RegisterPage enterUserName(String userName) {
        eActions.sendText(userNameField, userName);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        eActions.sendText(passwordField, password);
        return this;
    }

    public RegisterPage enterConfirmPassword(String password) {
        eActions.sendText(confirmPasswordField, password);
        return this;
    }

    public RegisterPage enterFullName(String fullName) {
        eActions.sendText(fullNameField, fullName);
        return this;
    }

    public RegisterPage enterEmail(String email) {
        eActions.sendText(emailField, email);
        return this;
    }

    public RegisterPage clickCaptchaRefresh() {
        eActions.clickWebElement(refreshCaptchaImage);
        return this;
    }

    public RegisterPage enterCaptchaText(String captchaText) {
        eActions.sendText(captchaTextField, captchaText);
        return this;
    }

    public RegisterPage solveCaptchaManually() {
        eActions.solveCaptchaTextManually(successfullyRegistrationMsg, 60);
        return this;
    }

    public RegisterPage clickTermsCTA() {
        eActions.clickWebElement(termsAndConditionsCTA);
        return this;
    }

    public RegisterPage markAgreeTerms() {
        eActions.clickWebElement(checkAgreementTermsAndConditions);
        return this;
    }

    public RegisterPage clickRegisterButton() {
        eActions.clickWebElement(registerButton);
        return this;
    }

    public RegisterPage clickResetButton() {
        eActions.clickWebElement(resetButton);
        return this;
    }

    public HomePage clickBackToLoginPageAfterRegister(){
        eActions.clickWebElement(afterRegisterBackToLoginPage);
        return new HomePage(driver);
    }
    //</editor-fold>


    //<editor-fold desc="Getters">
    public String getUserNameFieldCurrentText() {
        return eActions.getElementDomPropertyValue(userNameField, VALUE_ATTRIBUTE);
    }

    public String getPasswordFieldCurrentText() {
        return eActions.getElementDomPropertyValue(passwordField, VALUE_ATTRIBUTE);
    }

    public String getConfirmPasswordFieldCurrentText() {
        return eActions.getElementDomPropertyValue(confirmPasswordField, VALUE_ATTRIBUTE);
    }

    public String getFullNameFieldCurrentText() {
        return eActions.getElementDomPropertyValue(fullNameField, VALUE_ATTRIBUTE);
    }

    public String getEmailFieldCurrentText() {
        return eActions.getElementDomPropertyValue(emailField, VALUE_ATTRIBUTE);
    }

    public String getCaptchaFieldCurrentText() {
        return eActions.getElementDomPropertyValue(captchaTextField, VALUE_ATTRIBUTE);
    }

    public String getUserNameErrorMsgText() {
        return eActions.getElementText(errorMsgUserName);
    }

    public String getPasswordErrorMsgText() {
        return eActions.getElementText(errorMsgPassword);
    }

    public String getConfirmPasswordErrorMsgText() {
        return eActions.getElementText(errorMsgConfirmPassword);
    }

    public String getFullNameErrorMsgText() {
        return eActions.getElementText(errorMsgFullName);
    }

    public String getEmailErrorMsgText() {
        return eActions.getElementText(errorMsgEmail);
    }

    public String getCaptchaErrorMsgText() {
        return eActions.getElementText(errorMsgCaptcha);
    }

    public String getMustAgreeTermsErrorMsgText() {
        return eActions.getElementText(errorMsgMustAgreeTerms);
    }

    public String getSuccessfullyRegistrationMsgText() {
        return eActions.getElementText(successfullyRegistrationMsg);
    }
    //</editor-fold>


    //<editor-fold desc="Validations & Errors">
    public boolean isCaptchaVisible() {
        return eActions.checkElementVisibility(captchaImage);
    }

    public boolean isUserNameErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgUserName);
    }

    public boolean isPasswordErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgPassword);
    }

    public boolean isConfirmPasswordErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgConfirmPassword);
    }

    public boolean isFullNameErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgFullName);
    }

    public boolean isEmailErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgEmail);
    }


    public boolean isCaptchaErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgCaptcha);
    }

    public boolean isMustAgreeTermsErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgMustAgreeTerms);
    }

    public boolean isSuccessfullyRegistrationMsgVisible() {
        return eActions.checkElementVisibility(successfullyRegistrationMsg);
    }

    public boolean isRegisterBtnClickable() {
        return eActions.checkElementClickability(registerButton);
    }

    public boolean isResetBtnClickable() {
        return eActions.checkElementClickability(resetButton);
    }

    public boolean isAgreeTermsSelected() {
        return eActions.checkElementSelected(checkAgreementTermsAndConditions);
    }
    //</editor-fold>


    //<editor-fold desc="Business Workflows">
    public RegisterPage fillRegistrationForm(
            String username, String password, String confirmPassword,
            String fullName, String email, String captchaText, boolean agreeTerms) {

        return enterUserName(username)
                .enterPassword(password)
                .enterConfirmPassword(confirmPassword)
                .enterFullName(fullName)
                .enterEmail(email)
                .markAgreeTerms()
                .enterCaptchaText(captchaText);
    }

    public RegisterPage fillRegistrationFormAndSubmit(
            String username, String password, String confirmPassword,
            String fullName, String email, String captchaText, boolean agreeTerms) {

        enterUserName(username)
                .enterPassword(password)
                .enterConfirmPassword(confirmPassword)
                .enterFullName(fullName)
                .enterEmail(email);

        if (agreeTerms) markAgreeTerms();

        if (captchaText != null && !captchaText.isEmpty()) {
            return enterCaptchaText(captchaText)
                    .clickRegisterButton();
        } else {
            return solveCaptchaManually();
        }
    }
    //</editor-fold>
}