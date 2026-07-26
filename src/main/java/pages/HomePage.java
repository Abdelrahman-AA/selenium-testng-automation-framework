package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    //<editor-fold desc="Page Objects">
    public HomePage(WebDriver driver) {
        super(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Locators">
    private final By logo = By.cssSelector("img[alt='AdactIn Group']");
    private final By newUserRegisterCTA = By.xpath("//a[normalize-space()='New User Register Here']");
    private final By userNameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login");
    private final By forgetPasswordCTA = By.xpath("//a[normalize-space()='Forgot Password?']");
    private final By errorMsgUserName = By.id("username_span");
    private final By errorMsgPassword = By.id("password_span");
    private final By errorMsgInvalidLoginDetails = By.cssSelector("div[class='auth_error'] b");
    //</editor-fold>

    //<editor-fold desc="Actions">
    public HomePage openHomePageURL(String homePageUrl) {
        eActions.getToUrl(homePageUrl);
        return this;
    }

    public HomePage enterUserName(String userName) {
        eActions.sendText(userNameField, userName);
        return this;
    }

    public HomePage enterPassword(String password) {
        eActions.sendText(passwordField, password);
        return this;
    }

    public RegisterPage clickNewUserCTA() {
        eActions.clickWebElement(newUserRegisterCTA);
        return new RegisterPage(driver);
    }

    public ForgetPasswordPage clickForgetPasswordCTA() {
        eActions.clickWebElement(forgetPasswordCTA);
        return new ForgetPasswordPage(driver);
    }

    public SearchHotelPage clickLoginButton() {
        eActions.clickWebElement(loginButton);
        return new SearchHotelPage(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getUserNameErrorMsgText() {
        return eActions.getElementText(errorMsgUserName);
    }

    public String getPasswordErrorMsgText() {
        return eActions.getElementText(errorMsgPassword);
    }

    public String getInvalidLoginDetailsErrorMsgText() {
        return eActions.getElementText(errorMsgInvalidLoginDetails);
    }
    //</editor-fold>

    //<editor-fold desc="Validations & Errors">
    public boolean isLogoVisible() {
        return eActions.checkElementVisibility(logo);
    }

    public boolean isUserNameErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgUserName);
    }

    public boolean isPasswordErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgPassword);
    }

    public boolean isInvalidLoginDetailsErrorMsgVisible() {
        return eActions.checkElementVisibility(errorMsgInvalidLoginDetails);
    }
    //</editor-fold>

    //<editor-fold desc="Business Workflows">
    public SearchHotelPage fillLoginFormAndSubmit(String userName, String password) {
       return enterUserName(userName)
        .enterPassword(password)
                .clickLoginButton();
    }

    public SearchHotelPage openHomePageAndLoginWithValidRegisteredCredentials(String userName, String originalPass, String homePageURL) {
        return openHomePageURL(homePageURL)
        .fillLoginFormAndSubmit(userName, originalPass);
    }
    //</editor-fold>
}