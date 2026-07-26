package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static utils.helpers.Helpers.isCurrentUrlEqualTo;

public class LogoutPageTest extends BaseTest {

    @BeforeMethod
    public void initializePage() {
        logoutPage = homePage
                .openHomePageAndLoginWithValidRegisteredCredentials(registeredUserName, registeredPassword, homePageURL)
                .staticBar
                .clickLogoutCTA();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Go To Logout WebPage",
            threadPoolSize = threadPoolSize)
    public void verifyGoingToLogoutPage() {
        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), logoutURL), "User was not redirected to the Logout page after clicking Logout CTA.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Logout Page Msg Appears",
            threadPoolSize = threadPoolSize)
    public void verifySuccessfullyLogoutPageMsgIsVisible() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), logoutURL), "User was not redirected to the Logout page.");
        softAssert.assertTrue(logoutPage.isSuccessfullyLoggedOutMsgVisible(), "Successfully logged out message is not displayed on the Logout page.");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Going To HomePage When Clicking On Login Again CTA",
            threadPoolSize = threadPoolSize)
    public void verifyGoingToHomePageByClickingOnLoginAgainCta() {
        logoutPage
                .clickToLoginAgainCTA();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), homePageURL + "index.php"), "Failed to navigate back to the Home/Login page after clicking 'Click here to login again'.");
    }
}