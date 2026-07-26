package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testData.TestData;

import static utils.helpers.Helpers.isCurrentUrlEqualTo;

public class HomePageTest extends BaseTest {

    private final String userNameEmptyErrorMsg = TestData.get("Pages.HomePage.Errors.EmptyUserName");
    private final String passwordEmptyErrorMsg = TestData.get("Pages.HomePage.Errors.EmptyPassword");
    private final String registeredUserName = TestData.get("TestData.ValidRegisteredAccount.UserName");
    private final String registeredPassword = TestData.get("TestData.ValidRegisteredAccount.Password");
    private final String notRegisteredUserName = TestData.get("TestData.NotRegisteredAccount.UserName");
    private final String notRegisteredPassword = TestData.get("TestData.NotRegisteredAccount.Password");
    private final String empty = "";

    @BeforeMethod
    public void initializePage() {
        homePage
                .openHomePageURL(homePageURL);
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Go To Website WebPage",
            threadPoolSize = threadPoolSize)
    public void verifyHomePageOpened() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), homePageURL), "Home Page URL does not match expected URL.");
        softAssert.assertTrue(homePage.isLogoVisible(), "Home Page logo is not visible.");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Logging With Valid Data",
            threadPoolSize = threadPoolSize)
    public void verifyLoggingWithValidData() {
        homePage
                .fillLoginFormAndSubmit(registeredUserName, registeredPassword);

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), searchHotelPageURL), "User was not redirected to the Search Hotel page after valid login.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Going To Register Page From HomePage",
            threadPoolSize = threadPoolSize)
    public void verifyGoingToRegisterPageFromHomePage() {
        homePage
                .clickNewUserCTA();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), registerPageURL), "Failed to navigate to Register page from Home Page.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Going To Forget Logging Info Page From HomePage",
            threadPoolSize = threadPoolSize)
    public void verifyGoingToForgetLoggingInfoPageFromHomePage() {
        homePage
                .clickForgetPasswordCTA();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), forgetPasswordURL), "Failed to navigate to Forgot Password page from Home Page.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Try To Login With Empty Data",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgAppearanceWithEmptyLoginData() {
        homePage
                .clickLoginButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(homePage.isUserNameErrorMsgVisible(), "Username error message should be visible for empty login.");
        softAssert.assertEquals(homePage.getUserNameErrorMsgText(), userNameEmptyErrorMsg, "Username error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Try To Login With Registered UserName And Empty Password",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgAppearanceWithRegisteredUsernameAndEmptyPassword() {
        homePage
                .fillLoginFormAndSubmit(registeredUserName, empty);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(homePage.isUserNameErrorMsgVisible(), "Username error message should NOT be visible when username is provided.");
        softAssert.assertTrue(homePage.isPasswordErrorMsgVisible(), "Password error message should be visible when password field is empty.");
        softAssert.assertEquals(homePage.getPasswordErrorMsgText(), passwordEmptyErrorMsg, "Password error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Try To Login With Not Registered UserName And Empty Password",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgAppearanceWithNotRegisteredUsernameAndEmptyPassword() {
        homePage
                .fillLoginFormAndSubmit(notRegisteredUserName, empty);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(homePage.isUserNameErrorMsgVisible(), "Username error message should NOT be visible when username is provided.");
        softAssert.assertTrue(homePage.isPasswordErrorMsgVisible(), "Password error message should be visible when password field is empty.");
        softAssert.assertEquals(homePage.getPasswordErrorMsgText(), passwordEmptyErrorMsg, "Password error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Try To Login With Empty UserName And Valid Password",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgAppearanceWithEmptyUsernameAndValidPassword() {
        homePage
                .fillLoginFormAndSubmit(empty, registeredPassword);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(homePage.isUserNameErrorMsgVisible(), "Username error message should be visible when username field is empty.");
        softAssert.assertFalse(homePage.isPasswordErrorMsgVisible(), "Password error message should NOT be visible when password is provided.");
        softAssert.assertEquals(homePage.getUserNameErrorMsgText(), userNameEmptyErrorMsg, "Username error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Try To Login With InValid UserName And Password",
            threadPoolSize = threadPoolSize)
    public void verifyNotLoggingWithInvalidData() {
        homePage
                .fillLoginFormAndSubmit(notRegisteredUserName, notRegisteredPassword);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(homePage.isInvalidLoginDetailsErrorMsgVisible(), "Invalid login error message should be displayed.");
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), homePageURL), "User should remain on the Home Page after an invalid login attempt.");
        softAssert.assertAll();
    }
}