package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testData.TestData;

import static utils.helpers.Helpers.getRandomString;
import static utils.helpers.Helpers.isCurrentUrlEqualTo;

public class ForgetPasswordTest extends BaseTest {

    @BeforeMethod
    public void initializePage() {
        forgetPasswordPage = homePage
                .openHomePageURL(homePageURL)
                .clickForgetPasswordCTA();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Receive Email To Reset Password",
            threadPoolSize = threadPoolSize)
    public void verifySendingEmailToResetPasswordWhenEnteringRegisteredEmail() {
        String randomString = getRandomString(3);
        forgetPasswordPage
                .clickBackToLoginPageCta()
                .clickNewUserCTA()
                .fillRegistrationFormAndSubmit(TestData.get("TestData.ValidRegistration.UserName") + randomString,
                        TestData.get("TestData.ValidRegistration.Password"),
                        TestData.get("TestData.ValidRegistration.ConfirmPassword"),
                        TestData.get("TestData.ValidRegistration.FullName"),
                        randomString + TestData.get("TestData.ValidRegistration.Email"),
                        "",
                        true)
                .clickBackToLoginPageAfterRegister()
                .clickForgetPasswordCTA()
                .enterEmailAndSubmit(randomString + TestData.get("TestData.ValidRegistration.Email"));

        Assert.assertTrue(forgetPasswordPage.isSuccessfullyEmailedPasswordMsgVisible(),
                "Successfully emailed password message should be visible after submitting registered email.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Still Able To login With Original Password When Received Email To Reset Password And Dont Reset It",
            threadPoolSize = threadPoolSize)
    public void verifyStillAbleTOLoginWithOriginalPasswordWhileNotResetPasswordFromEmail() {
        String randomString = getRandomString(3);
        searchHotelPage = forgetPasswordPage
                .clickBackToLoginPageCta()
                .clickNewUserCTA()
                .fillRegistrationFormAndSubmit(TestData.get("TestData.ValidRegistration.UserName") + randomString,
                        TestData.get("TestData.ValidRegistration.Password"),
                        TestData.get("TestData.ValidRegistration.ConfirmPassword"),
                        TestData.get("TestData.ValidRegistration.FullName"),
                        randomString + TestData.get("TestData.ValidRegistration.Email"),
                        "",
                        true)
                .clickBackToLoginPageAfterRegister()
                .clickForgetPasswordCTA()
                .enterEmailAndSubmit(randomString + TestData.get("TestData.ValidRegistration.Email"))
                .clickBackToLoginPageAfterEmailPassword()
                .openHomePageAndLoginWithValidRegisteredCredentials(TestData.get("TestData.ValidRegistration.UserName"),
                        TestData.get("TestData.ValidRegistration.Password"), homePageURL);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(homePage.isInvalidLoginDetailsErrorMsgVisible(),
                "Invalid login details error message should not be visible when logging in with original password.");
        softAssert.assertTrue(searchHotelPage.staticBar.isStaticBarVisible(),
                "Static bar should be visible on search hotel page after successful login.");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Empty Email Field When Click Reset When Valid Email",
            threadPoolSize = threadPoolSize)
    public void verifyResetEmailFieldAfterEnterEmailAndClickResetWhenValidEmail() {
        forgetPasswordPage
                .enterEmailField(TestData.get("TestData.ValidRegistration.Email"))
                .clickResetButtonCta();

        Assert.assertTrue(forgetPasswordPage.getEmailFieldText().isEmpty(),
                "Email field should be empty after clicking the reset button with a valid email.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Navigate Back To Login Page When Clicking Back To Login CTA",
            threadPoolSize = threadPoolSize)
    public void verifyNavigateBackToLoginPageWhenClickingBackToLoginCTA() {
        forgetPasswordPage
                .clickBackToLoginPageCta();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), homePageURL + "index.php"),
                "User should be redirected back to the login page and login elements should be visible.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Remain Empty Email Field When Click Reset When Email Field Is Empty",
            threadPoolSize = threadPoolSize)
    public void verifyResetEmailFieldWhenClickResetWhileEmailFieldIsEmpty() {
        forgetPasswordPage
                .clickResetButtonCta();

        Assert.assertTrue(forgetPasswordPage.getEmailFieldText().isEmpty(),
                "Email field should remain empty when clicking reset while the field is already empty.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg Appear When Enter Not Registered Email With Msg Statement Content",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgAppearWhenEnterNotRegisteredEmailWithMsgStatementContent() {
        String randomString = getRandomString(3);
        forgetPasswordPage
                .enterEmailAndSubmit(randomString + TestData.get("TestData.ValidRegistration.Email"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(forgetPasswordPage.isEmailFieldErrorMsgVisible(),
                "Email field error message should be visible when entering a non-registered email.");
        softAssert.assertEquals(forgetPasswordPage.getEmailFieldErrorMsg(),
                TestData.get("Pages.ForgetPassword.Errors.NotRegisteredEmail"),
                "Email field error message content does not match the expected value for a non-registered email.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg Appear When Enter Empty Email With Msg Statement Content",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgAppearWhenEnterEmptyEmailWithMsgStatementContent() {
        forgetPasswordPage
                .enterEmailAndSubmit(empty);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(forgetPasswordPage.isEmailFieldErrorMsgVisible(),
                "Email field error message should be visible when entering an empty email.");
        softAssert.assertEquals(forgetPasswordPage.getEmailFieldErrorMsg(),
                TestData.get("Pages.ForgetPassword.Errors.EmptyEmail"),
                "Email field error message content does not match the expected value for an empty email.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg Appear When Enter Wrong Email Format With Msg Statement Content",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgAppearWhenEnterWrongEmailFormatWithMsgStatementContent() {
        forgetPasswordPage
                .enterEmailAndSubmit(TestData.get("TestData.InvalidShortRegistration.Email"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(forgetPasswordPage.isEmailFieldErrorMsgVisible(),
                "Email field error message should be visible when entering an invalid email format.");
        softAssert.assertEquals(forgetPasswordPage.getEmailFieldErrorMsg(),
                TestData.get("Pages.ForgetPassword.Errors.InvalidEmail"),
                "Email field error message content does not match the expected value for an invalid email format.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Empty Email Field When Click Reset When InValid Email",
            threadPoolSize = threadPoolSize)
    public void verifyResetEmailFieldAfterEnterEmailAndClickResetWhenInValidEmail() {
        forgetPasswordPage
                .enterEmailField(TestData.get("TestData.InvalidShortRegistration.Email"))
                .clickResetButtonCta();

        Assert.assertTrue(forgetPasswordPage.getEmailFieldText().isEmpty(),
                "Email field should be empty after clicking the reset button with an invalid email.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Empty Email Field When Click Reset After Email Field Error Msg Appear When InValid Email Format",
            threadPoolSize = threadPoolSize)
    public void verifyResetEmailFieldAfterEnterEmailAndClickResetAfterEmailFieldErrorMsgAppearWhenInValidEmailFormat() {
        forgetPasswordPage
                .enterEmailField(TestData.get("TestData.InvalidShortRegistration.Email"))
                .clickEmailPasswordButtonCta();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(forgetPasswordPage.isEmailFieldErrorMsgVisible(),
                "Email field error message should be visible before resetting the field.");

        forgetPasswordPage
                .clickResetButtonCta();

        softAssert.assertTrue(forgetPasswordPage.getEmailFieldText().isEmpty(),
                "Email field should be empty after clicking the reset button following an invalid email format error.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Empty Email Field When Click Reset After Email Field Error Msg Appear When Not Registered Email Format",
            threadPoolSize = threadPoolSize)
    public void verifyResetEmailFieldAfterEnterEmailAndClickResetAfterEmailFieldErrorMsgAppearWhenNotRegisteredEmailFormat() {
        String randomString = getRandomString(3);
        forgetPasswordPage
                .enterEmailField(randomString + TestData.get("TestData.ValidRegistration.Email"))
                .clickEmailPasswordButtonCta();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(forgetPasswordPage.isEmailFieldErrorMsgVisible(),
                "Email field error message should be visible for non-registered email before resetting.");

        forgetPasswordPage
                .clickResetButtonCta();

        softAssert.assertTrue(forgetPasswordPage.getEmailFieldText().isEmpty(),
                "Email field should be empty after clicking the reset button following a non-registered email error.");
        softAssert.assertAll();
    }
}