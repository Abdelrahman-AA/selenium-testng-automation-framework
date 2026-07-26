package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testData.TestData;

import static utils.helpers.Helpers.*;

public class RegisterPageTest extends BaseTest {

    @BeforeMethod
    public void initializePage() {
        registerPage = homePage
                .openHomePageURL(homePageURL)
                .clickNewUserCTA();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Go To Register Page WebPage",
            threadPoolSize = threadPoolSize)
    public void verifyRegistrationPageOpened() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), registerPageURL),
                "Navigation Error: Register page URL mismatch.");
        softAssert.assertTrue(registerPage.isCaptchaVisible(),
                "UI Element Error: CAPTCHA container or element is not displayed.");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Go Back To HomePage When Click Back To Home Page CTA",
            threadPoolSize = threadPoolSize)
    public void verifyGoingBackToHomePageFromCta() {
        registerPage
                .clickBackToLoginPageCTA();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), homePageURL + "index.php"),
                "Navigation Error: Failed to return to Home Page via 'Back to Login' CTA.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Opening Terms Page When Click Terms CTA",
            threadPoolSize = threadPoolSize)
    public void verifyOpeningTermsPageFromRegistrationPageCta() {
        registerPage
                .clickTermsCTA();

        String originalWindow = switchToTheNewWindowAtSameSession(getDriver());

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), termsConditionsURL),
                "Navigation Error: Terms and Conditions URL did not match in the newly opened tab.");

        getDriver().close();
        getDriver().switchTo().window(originalWindow);
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Input Fields Cleared When Click Reset Button",
            threadPoolSize = threadPoolSize)
    public void verifyInputFieldsClearedAfterClickReset() {
        registerPage
                .fillRegistrationForm(
                        TestData.get("TestData.ValidRegistration.UserName"),
                        TestData.get("TestData.ValidRegistration.Password"),
                        TestData.get("TestData.ValidRegistration.ConfirmPassword"),
                        TestData.get("TestData.ValidRegistration.FullName"),
                        TestData.get("TestData.ValidRegistration.Email"),
                        TestData.get("TestData.ValidRegistration.RandomCaptcha"),
                        true)
                .clickResetButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.getUserNameFieldCurrentText().isEmpty(), "Reset Action Failure: Username input was not cleared.");
        softAssert.assertTrue(registerPage.getPasswordFieldCurrentText().isEmpty(), "Reset Action Failure: Password input was not cleared.");
        softAssert.assertTrue(registerPage.getConfirmPasswordFieldCurrentText().isEmpty(), "Reset Action Failure: Confirm Password input was not cleared.");
        softAssert.assertTrue(registerPage.getFullNameFieldCurrentText().isEmpty(), "Reset Action Failure: Full Name input was not cleared.");
        softAssert.assertTrue(registerPage.getEmailFieldCurrentText().isEmpty(), "Reset Action Failure: Email input was not cleared.");
        softAssert.assertTrue(registerPage.getCaptchaFieldCurrentText().isEmpty(), "Reset Action Failure: CAPTCHA input was not cleared.");
        softAssert.assertFalse(registerPage.isAgreeTermsSelected(), "Reset Action Failure: Terms checkbox remained selected after reset.");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Edit UserName At Registration Form After Invalid Input And Submit Button Still Clickable",
            threadPoolSize = threadPoolSize)
    public void verifyCanEditUserNameAtRegistrationFormAfterInvalidInputAndSubmitBtnStillClickable() {
        registerPage
                .enterUserName(TestData.get("TestData.InvalidShortRegistration.UserName"))
                .clickRegisterButton()
                .enterUserName(TestData.get("TestData.ValidRegistration.UserName"));

        Assert.assertTrue(registerPage.isRegisterBtnClickable(), "UI State Error: Register button is disabled or unclickable after editing Username.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Edit Password At Registration Form After Invalid Input And Submit Button Still Clickable",
            threadPoolSize = threadPoolSize)
    public void verifyCanEditPasswordAtRegistrationFormAfterInvalidInputAndSubmitBtnStillClickable() {
        registerPage
                .enterPassword(TestData.get("TestData.InvalidShortRegistration.Password"))
                .clickRegisterButton()
                .enterPassword(TestData.get("TestData.ValidRegistration.Password"));

        Assert.assertTrue(registerPage.isRegisterBtnClickable(), "UI State Error: Register button is disabled or unclickable after editing Password.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Edit Confirm Password At Registration Form After Invalid Input And Submit Button Still Clickable",
            threadPoolSize = threadPoolSize)
    public void verifyCanEditConfirmPasswordAtRegistrationFormAfterInvalidInputAndSubmitBtnStillClickable() {
        registerPage
                .enterConfirmPassword(TestData.get("TestData.InvalidShortRegistration.ConfirmPassword"))
                .clickRegisterButton()
                .enterConfirmPassword(TestData.get("TestData.ValidRegistration.ConfirmPassword"));

        Assert.assertTrue(registerPage.isRegisterBtnClickable(), "UI State Error: Register button is disabled or unclickable after editing Confirm Password.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Edit Full Name At Registration Form After Invalid Input And Submit Button Still Clickable",
            threadPoolSize = threadPoolSize)
    public void verifyCanEditFullNameAtRegistrationFormAfterInvalidInputAndSubmitBtnStillClickable() {
        registerPage
                .enterFullName(TestData.get("TestData.InvalidShortRegistration.FullName"))
                .clickRegisterButton()
                .enterFullName(TestData.get("TestData.ValidRegistration.FullName"));

        Assert.assertTrue(registerPage.isRegisterBtnClickable(), "UI State Error: Register button is disabled or unclickable after editing Full Name.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Edit Email At Registration Form After Invalid Input And Submit Button Still Clickable",
            threadPoolSize = threadPoolSize)
    public void verifyCanEditEmailAtRegistrationFormAfterInvalidInputAndSubmitBtnStillClickable() {
        registerPage
                .enterEmail(TestData.get("TestData.InvalidShortRegistration.Email"))
                .clickRegisterButton()
                .enterEmail(TestData.get("TestData.ValidRegistration.Email"));

        Assert.assertTrue(registerPage.isRegisterBtnClickable(), "UI State Error: Register button is disabled or unclickable after editing Email.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Edit Captcha At Registration Form After Invalid Input And Submit Button Still Clickable",
            threadPoolSize = threadPoolSize)
    public void verifyCanEditCaptchaAtRegistrationFormAfterInvalidInputAndSubmitBtnStillClickable() {
        registerPage
                .enterCaptchaText(TestData.get("TestData.InvalidShortRegistration.RandomCaptcha"))
                .clickRegisterButton();

        Assert.assertTrue(registerPage.isRegisterBtnClickable(), "UI State Error: Register button is disabled or unclickable after editing CAPTCHA.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Can Edit Terms Agreement Check At Registration Form After Invalid Input And Submit Button Still Clickable",
            threadPoolSize = threadPoolSize)
    public void verifyCanEditTermsAgreementAtRegistrationFormAfterInvalidInputAndSubmitBtnStillClickable() {
        registerPage
                .markAgreeTerms()
                .markAgreeTerms()
                .clickRegisterButton()
                .markAgreeTerms();

        Assert.assertTrue(registerPage.isRegisterBtnClickable(), "UI State Error: Register button is disabled or unclickable after toggling Terms Agreement.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Register With Valid Data",
            threadPoolSize = threadPoolSize)
    public void verifySuccessfullyRegistration() {
        String randomString = getRandomString(3);

        registerPage
                .fillRegistrationFormAndSubmit(
                        TestData.get("TestData.ValidRegistration.UserName") + randomString,
                        TestData.get("TestData.ValidRegistration.Password"),
                        TestData.get("TestData.ValidRegistration.ConfirmPassword"),
                        TestData.get("TestData.ValidRegistration.FullName"),
                        randomString + TestData.get("TestData.ValidRegistration.Email"),
                        "",
                        true);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.isSuccessfullyRegistrationMsgVisible(), "Execution Failure: Registration success banner not displayed.");
        softAssert.assertTrue(registerPage.getSuccessfullyRegistrationMsgText().contains(TestData.get("Pages.RegisterPage.Success.Registration")),
                "UI Message Mismatch: Expected success message content was not found.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Try To Register With Empty Data",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgWithEmptyData() {
        registerPage
                .clickRegisterButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.isUserNameErrorMsgVisible(), "Validation Missing: Username error message is not visible.");
        softAssert.assertTrue(registerPage.isPasswordErrorMsgVisible(), "Validation Missing: Password error message is not visible.");
        softAssert.assertTrue(registerPage.isConfirmPasswordErrorMsgVisible(), "Validation Missing: Confirm password error message is not visible.");
        softAssert.assertTrue(registerPage.isFullNameErrorMsgVisible(), "Validation Missing: Full Name error message is not visible.");
        softAssert.assertTrue(registerPage.isEmailErrorMsgVisible(), "Validation Missing: Email error message is not visible.");
        softAssert.assertTrue(registerPage.isCaptchaErrorMsgVisible(), "Validation Missing: CAPTCHA error message is not visible.");
        softAssert.assertTrue(registerPage.isMustAgreeTermsErrorMsgVisible(), "Validation Missing: Terms Agreement error message is not visible.");

        softAssert.assertEquals(registerPage.getUserNameErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.EmptyUserName"), "Text Mismatch: Username empty error.");
        softAssert.assertEquals(registerPage.getPasswordErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.EmptyPassword"), "Text Mismatch: Password empty error.");
        softAssert.assertEquals(registerPage.getConfirmPasswordErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.EmptyConfirmPassword"), "Text Mismatch: Confirm Password empty error.");
        softAssert.assertEquals(registerPage.getFullNameErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.EmptyFullName"), "Text Mismatch: Full Name empty error.");
        softAssert.assertEquals(registerPage.getEmailErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.EmptyEmail"), "Text Mismatch: Email empty error.");
        softAssert.assertEquals(registerPage.getCaptchaErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.EmptyCaptcha"), "Text Mismatch: CAPTCHA empty error.");
        softAssert.assertEquals(registerPage.getMustAgreeTermsErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.NotAgreeTerms"), "Text Mismatch: Terms Agreement empty error.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Enter Wrong Email Format",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForWrongEmailFormat() {
        registerPage
                .enterEmail(TestData.get("TestData.InvalidShortRegistration.Email"))
                .clickRegisterButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.isEmailErrorMsgVisible(), "Validation Failure: Email error message container is not visible.");
        softAssert.assertEquals(registerPage.getEmailErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.InvalidEmail"), "Text Mismatch: Invalid Email error text.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Enter Wrong Captcha",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForWrongCaptcha() {
        registerPage
                .enterCaptchaText(TestData.get("TestData.InvalidShortRegistration.RandomCaptcha"))
                .clickRegisterButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.isCaptchaErrorMsgVisible(), "Validation Failure: CAPTCHA error message container is not visible.");
        softAssert.assertEquals(registerPage.getCaptchaErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.InvalidCaptcha"), "Text Mismatch: Invalid CAPTCHA error text.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msgs With Statement Content Appear When Enter InValid Short Data For All Fields But With Valid Email",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForEnteringShortDataButEmailForRegistration() {
        registerPage
                .fillRegistrationFormAndSubmit(
                        TestData.get("TestData.InvalidShortRegistration.UserName"),
                        TestData.get("TestData.InvalidShortRegistration.Password"),
                        TestData.get("TestData.InvalidShortRegistration.ConfirmPassword"),
                        TestData.get("TestData.InvalidShortRegistration.FullName"),
                        TestData.get("TestData.ValidRegistration.Email"),
                        TestData.get("TestData.InvalidShortRegistration.RandomCaptcha"),
                        true);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.isUserNameErrorMsgVisible(), "Validation Failure: Short Username error message not displayed.");
        softAssert.assertTrue(registerPage.isPasswordErrorMsgVisible(), "Validation Failure: Short Password error message not displayed.");
        softAssert.assertTrue(registerPage.isFullNameErrorMsgVisible(), "Validation Failure: Short Full Name error message not displayed.");
        softAssert.assertTrue(registerPage.isCaptchaErrorMsgVisible(), "Validation Failure: Invalid CAPTCHA error message not displayed.");

        softAssert.assertEquals(registerPage.getUserNameErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.ShortUserName"), "Text Mismatch: Short Username error text.");
        softAssert.assertEquals(registerPage.getPasswordErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.ShortPassword"), "Text Mismatch: Short Password error text.");
        softAssert.assertEquals(registerPage.getFullNameErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.ShortFullName"), "Text Mismatch: Short Full Name error text.");
        softAssert.assertEquals(registerPage.getCaptchaErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.InvalidCaptcha"), "Text Mismatch: Invalid CAPTCHA error text.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Enter Password Miss Match Confirm Password",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForMissMatchPasswordConfirmationAtRegistration() {
        registerPage
                .fillRegistrationFormAndSubmit(
                        TestData.get("TestData.ValidRegistration.UserName"),
                        TestData.get("TestData.ValidRegistration.Password"),
                        TestData.get("TestData.ValidRegistration.MismatchConfirm"),
                        TestData.get("TestData.ValidRegistration.FullName"),
                        TestData.get("TestData.ValidRegistration.Email"),
                        TestData.get("TestData.ValidRegistration.RandomCaptcha"),
                        true);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.isConfirmPasswordErrorMsgVisible(), "Validation Failure: Mismatched password error message not displayed.");
        softAssert.assertTrue(registerPage.isCaptchaErrorMsgVisible(), "Validation Failure: Invalid CAPTCHA error message not displayed.");
        softAssert.assertEquals(registerPage.getConfirmPasswordErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.ConfirmPasswordNotMatch"), "Text Mismatch: Mismatched password error text.");
        softAssert.assertEquals(registerPage.getCaptchaErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.InvalidCaptcha"), "Text Mismatch: Invalid CAPTCHA error text.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Trying To Register With UserName Already Registered",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForRegisteredUserNameAtRegistration() {
        registerPage
                .fillRegistrationFormAndSubmit(
                        TestData.get("TestData.ValidRegisteredAccount.UserName"),
                        TestData.get("TestData.ValidRegistration.Password"),
                        TestData.get("TestData.ValidRegistration.ConfirmPassword"),
                        TestData.get("TestData.ValidRegistration.FullName"),
                        TestData.get("TestData.ValidRegistration.Email"),
                        TestData.get("TestData.ValidRegistration.RandomCaptcha"),
                        true);


        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.isUserNameErrorMsgVisible(), "Validation Failure: Duplicate Username error message not displayed.");
        softAssert.assertTrue(registerPage.isCaptchaErrorMsgVisible(), "Validation Failure: Invalid CAPTCHA error message not displayed.");
        softAssert.assertEquals(registerPage.getUserNameErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.UsedUserName"), "Text Mismatch: Duplicate Username error text.");
        softAssert.assertEquals(registerPage.getCaptchaErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.InvalidCaptcha"), "Text Mismatch: Invalid CAPTCHA error text.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Trying To Register With Email Already Registered",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForRegisteredEmailAtRegistration() {
        registerPage
                .fillRegistrationFormAndSubmit(
                        TestData.get("TestData.ValidRegistration.UserName"),
                        TestData.get("TestData.ValidRegistration.Password"),
                        TestData.get("TestData.ValidRegistration.ConfirmPassword"),
                        TestData.get("TestData.ValidRegistration.FullName"),
                        TestData.get("TestData.ValidRegisteredAccount.Email"),
                        TestData.get("TestData.ValidRegistration.RandomCaptcha"),
                        true);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.isEmailErrorMsgVisible(), "Validation Failure: Duplicate Email error message not displayed.");
        softAssert.assertTrue(registerPage.isCaptchaErrorMsgVisible(), "Validation Failure: Invalid CAPTCHA error message not displayed.");
        softAssert.assertEquals(registerPage.getEmailErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.UsedEmail"), "Text Mismatch: Duplicate Email error text.");
        softAssert.assertEquals(registerPage.getCaptchaErrorMsgText(), TestData.get("Pages.RegisterPage.Errors.InvalidCaptcha"), "Text Mismatch: Invalid CAPTCHA error text.");
        softAssert.assertAll();
    }
}