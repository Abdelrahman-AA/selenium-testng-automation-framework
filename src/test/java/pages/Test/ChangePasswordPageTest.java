package pages.Test;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testData.TestData;

import static utils.helpers.NewBrowserSessions.canLoginInNewSession;
import static utils.helpers.SessionHelper.getPhpSessionId;
import static utils.helpers.TestDataCleanUp.resetPasswordViaApi;

public class ChangePasswordPageTest extends BaseTest {
    private String sessionId;
    private String temporaryPass1;
    private String temporaryPass2;
    private final String originalPass = TestData.get("TestData.ValidRegisteredAccount.Password");
    private String currentPassValueTesting;
    private final String userName = TestData.get("TestData.ValidRegisteredAccount.UserName");
    private String errorMsgTesting;

    @BeforeMethod(alwaysRun = true)
    public void initializePage() {
        changePasswordPage = homePage
                .openHomePageAndLoginWithValidRegisteredCredentials(registeredUserName, registeredPassword, homePageURL)
                .staticBar.clickChangePasswordCTA();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownCleanUp() {
        if (sessionId != null) {
            if (temporaryPass1 != null) {
                resetPasswordViaApi(sessionId, temporaryPass1, originalPass);
            }
            if (temporaryPass2 != null && !temporaryPass2.equals(temporaryPass1)) {
                resetPasswordViaApi(sessionId, temporaryPass2, originalPass);
            }
        }
        sessionId = null;
        temporaryPass1 = null;
        temporaryPass2 = null;
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Successfully Msg With Statement Content Appears When Valid Data")
    public void verifyPasswordChangeSuccessfullyMsgWhenValidData() {
        temporaryPass1 = TestData.get("TestData.ValidChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.ValidChangePassword.ConfirmNewPassword");
        errorMsgTesting = TestData.get("Pages.ChangePasswordPage.Success.Updated");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(changePasswordPage.isSubmitChangePasswordMsgVisible(),
                "Execution Failure: Success message banner or container is not visible on the UI after valid submission.");
        Assert.assertEquals(changePasswordPage.getSubmitChangePasswordMsgText(), errorMsgTesting,
                "UI Message Mismatch: The displayed confirmation text does not match the expected success message for a valid password change.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Password Change Successfully When Valid Data")
    public void verifyPasswordChangedSuccessfullyWhenValidData() {
        temporaryPass1 = TestData.get("TestData.ValidChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.ValidChangePassword.ConfirmNewPassword");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(canLoginInNewSession(userName, temporaryPass1, homePageURL),
                "Functional Failure: Backend rejected or failed to persist valid data; system denied a new session login with the successfully updated password.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Confirm Password Is Empty")
    public void verifyPasswordChangeErrorMsgWhenConfirmPasswordIsEmpty() {
        temporaryPass1 = TestData.get("TestData.ValidChangePassword.NewPassword");
        temporaryPass2 = empty;
        errorMsgTesting = TestData.get("Pages.ChangePasswordPage.Errors.NewOrConfirmIsEmpty");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(changePasswordPage.isSubmitChangePasswordMsgVisible(),
                "Validation Failure: Change password status banner or message container is not visible on the UI.");
        Assert.assertEquals(changePasswordPage.getSubmitChangePasswordMsgText(), errorMsgTesting,
                "UI Message Mismatch: The displayed error message text does not match the expected validation for an empty 'Confirm Password' field.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Password Dont Change When Confirm Password Is Empty")
    public void verifyPasswordNotChangedWhenConfirmPasswordIsEmpty() {
        temporaryPass1 = TestData.get("TestData.ValidChangePassword.NewPassword");
        temporaryPass2 = empty;

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(canLoginInNewSession(userName, temporaryPass1, homePageURL),
                "Security Flaw: Backend accepted the update; system allowed a new session login using the unconfirmed new password.");
        softAssert.assertFalse(canLoginInNewSession(userName, temporaryPass2, homePageURL),
                "Data Integrity Error: System allowed a new session login using an empty password string.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When New Password Is Empty")
    public void verifyPasswordChangeErrorMsgWhenNewPasswordIsEmpty() {
        temporaryPass1 = empty;
        temporaryPass2 = TestData.get("TestData.ValidChangePassword.ConfirmNewPassword");
        errorMsgTesting = TestData.get("Pages.ChangePasswordPage.Errors.NewOrConfirmIsEmpty");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(changePasswordPage.isSubmitChangePasswordMsgVisible(),
                "Validation Failure: Change password status banner or message container is not visible on the UI.");
        Assert.assertEquals(changePasswordPage.getSubmitChangePasswordMsgText(), errorMsgTesting,
                "UI Message Mismatch: The displayed error message text does not match the expected validation for an empty 'New Password' field.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Password Dont Change When New Password Is Empty")
    public void verifyPasswordNotChangedWhenNewPasswordIsEmpty() {
        temporaryPass1 = empty;
        temporaryPass2 = TestData.get("TestData.ValidChangePassword.ConfirmNewPassword");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(canLoginInNewSession(userName, temporaryPass1, homePageURL),
                "Data Integrity Error: System allowed a new session login using an empty password string.");
        softAssert.assertFalse(canLoginInNewSession(userName, temporaryPass2, homePageURL),
                "Security Flaw: Backend accepted the update; system allowed a new session login using the orphan confirmation password.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When New Password And Confirm Password Are Empty")
    public void verifyPasswordChangeErrorMsgWhenNewPasswordAndConfirmPasswordAreEmpty() {
        temporaryPass1 = empty;
        temporaryPass2 = empty;
        errorMsgTesting = TestData.get("Pages.ChangePasswordPage.Errors.NewOrConfirmIsEmpty");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(changePasswordPage.isSubmitChangePasswordMsgVisible(),
                "Validation Failure: Change password status banner or message container is not visible on the UI.");
        Assert.assertEquals(changePasswordPage.getSubmitChangePasswordMsgText(), errorMsgTesting,
                "UI Message Mismatch: The displayed error message text does not match the expected validation when both fields are left empty.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Password Dont Change When New Password And Confirm Password Are Empty")
    public void verifyPasswordNotChangedWhenNewPasswordAndConfirmPasswordAreEmpty() {
        temporaryPass1 = empty;
        temporaryPass2 = empty;

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertFalse(canLoginInNewSession(userName, temporaryPass1, homePageURL),
                "Security Flaw: Backend processed an empty form submission; system allowed a new session login using an empty password.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When New Password Is InValid Short")
    public void verifyPasswordChangeErrorMsgWhenNewPasswordIsInValidShort() {
        temporaryPass1 = TestData.get("TestData.InvalidShortChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.InvalidShortChangePassword.ConfirmNewPassword");
        errorMsgTesting = TestData.get("Pages.ChangePasswordPage.Errors.ShortPassWord");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(changePasswordPage.isSubmitChangePasswordMsgVisible(),
                "Validation Failure: Change password status banner or message container is not visible on the UI.");
        Assert.assertEquals(changePasswordPage.getSubmitChangePasswordMsgText(), errorMsgTesting,
                "UI Message Mismatch: The displayed error message text does not match the expected validation for a password below minimum length.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Password Dont Change When New Password Is InValid Short")
    public void verifyPasswordNotChangedWhenNewPasswordIsInValidShort() {
        temporaryPass1 = TestData.get("TestData.InvalidShortChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.InvalidShortChangePassword.ConfirmNewPassword");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertFalse(canLoginInNewSession(userName, temporaryPass1, homePageURL),
                "Security Policy Violation: Backend stored the update; system allowed a new session login using a password that violates length policies.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When New Password Miss Match")
    public void verifyPasswordChangeErrorMsgWhenNewPasswordMissMatchConfirmPassword() {
        temporaryPass1 = TestData.get("TestData.InvalidNotMatchChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.InvalidNotMatchChangePassword.ConfirmNewPassword");
        errorMsgTesting = TestData.get("Pages.ChangePasswordPage.Errors.MismatchConfirmation");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(changePasswordPage.isSubmitChangePasswordMsgVisible(),
                "Validation Failure: Change password status banner or message container is not visible on the UI.");
        Assert.assertEquals(changePasswordPage.getSubmitChangePasswordMsgText(), errorMsgTesting,
                "UI Message Mismatch: The displayed error message text does not match the expected validation for mismatching passwords.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Password Dont Change When New Password Miss Match Confirm PassWord")
    public void verifyPasswordNotChangedWhenNewPasswordMissMatchConfirmPasswordByBothMissMatchedPasswords() {
        temporaryPass1 = TestData.get("TestData.InvalidNotMatchChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.InvalidNotMatchChangePassword.ConfirmNewPassword");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(originalPass, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(canLoginInNewSession(userName, temporaryPass1, homePageURL),
                "Security Flaw: Backend processed mismatched data; system allowed a new session login using the unmatched 'New Password'.");
        softAssert.assertFalse(canLoginInNewSession(userName, temporaryPass2, homePageURL),
                "Security Flaw: Backend processed mismatched data; system allowed a new session login using the unmatched 'Confirm Password'.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Current Password Is Worng")
    public void verifyPasswordChangeErrorMsgWhenCurrentPasswordIsWrong() {
        currentPassValueTesting = "adfj@#DOP";
        temporaryPass1 = TestData.get("TestData.ValidChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.ValidChangePassword.ConfirmNewPassword");
        errorMsgTesting = TestData.get("Pages.ChangePasswordPage.Errors.WrongCurrentPassword");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(currentPassValueTesting, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(changePasswordPage.isSubmitChangePasswordMsgVisible(),
                "Validation Failure: Change password status banner or message container is not visible on the UI.");
        Assert.assertEquals(changePasswordPage.getSubmitChangePasswordMsgText(), errorMsgTesting,
                "UI Message Mismatch: The displayed error message text does not match the expected validation for an incorrect 'Current Password'.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Password Dont Change When Current Password Is Wrong")
    public void verifyPasswordNotChangedWhenCurrentPasswordIsWrong() {
        currentPassValueTesting = "adfj@#DOP";
        temporaryPass1 = TestData.get("TestData.ValidChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.ValidChangePassword.ConfirmNewPassword");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(currentPassValueTesting, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertFalse(canLoginInNewSession(userName, temporaryPass1, homePageURL),
                "Authentication Bypass: Backend authorized the password change; system allowed a new session login despite a wrong current password.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Current Password Is Empty")
    public void verifyPasswordChangeErrorMsgWhenCurrentPasswordIsEmpty() {
        currentPassValueTesting = empty;
        temporaryPass1 = TestData.get("TestData.ValidChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.ValidChangePassword.ConfirmNewPassword");
        errorMsgTesting = TestData.get("Pages.ChangePasswordPage.Errors.WrongCurrentPassword");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(currentPassValueTesting, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(changePasswordPage.isSubmitChangePasswordMsgVisible(),
                "Validation Failure: Change password status banner or message container is not visible on the UI.");
        Assert.assertEquals(changePasswordPage.getSubmitChangePasswordMsgText(), errorMsgTesting,
                "UI Message Mismatch: The displayed error message text does not match the expected validation for an empty 'Current Password' field.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Password Dont Change When Current Password Is Empty")
    public void verifyPasswordNotChangedWhenCurrentPasswordIsEmpty() {
        currentPassValueTesting = empty;
        temporaryPass1 = TestData.get("TestData.ValidChangePassword.NewPassword");
        temporaryPass2 = TestData.get("TestData.ValidChangePassword.ConfirmNewPassword");

        changePasswordPage
                .fillChangePasswordFormAndSubmit(currentPassValueTesting, temporaryPass1, temporaryPass2);

        sessionId = getPhpSessionId(getDriver());

        Assert.assertFalse(canLoginInNewSession(userName, temporaryPass1, homePageURL),
                "Authentication Bypass: Backend authorized the password change; system allowed a new session login despite an empty current password field.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Error Msg With Statement Content Appears When Submit With Empty Fields")
    public void verifyPasswordChangeErrorMsgWhenSubmitWithEmptyFields() {
        currentPassValueTesting = empty;
        temporaryPass1 = empty;
        temporaryPass2 = empty;
        errorMsgTesting = TestData.get("Pages.ChangePasswordPage.Errors.WrongCurrentPassword");
        changePasswordPage.fillChangePasswordFormAndSubmit(currentPassValueTesting, temporaryPass1, temporaryPass2);
        sessionId = getPhpSessionId(getDriver());

        Assert.assertTrue(changePasswordPage.isSubmitChangePasswordMsgVisible(),
                "Validation Failure: Change password status banner or message container is not visible on the UI.");
        Assert.assertEquals(changePasswordPage.getSubmitChangePasswordMsgText(), errorMsgTesting,
                "UI Message Mismatch: The displayed error message text does not match the expected validation when submitting a fully empty form.");
    }
}