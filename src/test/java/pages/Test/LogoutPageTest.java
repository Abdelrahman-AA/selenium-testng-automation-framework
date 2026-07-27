package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static utils.helpers.Helpers.*;

public class LogoutPageTest extends BaseTest {

    @BeforeMethod
    public void initializePage() {
        searchHotelPage = homePage
                .openHomePageAndLoginWithValidRegisteredCredentials(registeredUserName, registeredPassword, homePageURL);
        logoutPage = searchHotelPage
                .staticBar
                .clickLogoutCTA();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Go To Logout WebPage")
    public void verifyGoingToLogoutPage() {
        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), logoutURL),
                "User was not redirected to the Logout page after clicking Logout CTA.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Logout Page Msg Appears")
    public void verifySuccessfullyLogoutPageMsgIsVisible() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), logoutURL),
                "User was not redirected to the Logout page.");
        softAssert.assertTrue(logoutPage.isSuccessfullyLoggedOutMsgVisible(),
                "Successfully logged out message is not displayed on the Logout page.");
        getBack(getDriver());
        softAssert.assertFalse(searchHotelPage.staticBar.isStaticBarVisible(),
                "Static bar should not be visible after going back from the logout page.");
        navigateToURL(getDriver(), searchHotelPageURL);
        softAssert.assertFalse(searchHotelPage.staticBar.isStaticBarVisible(),
                "Static bar should not be visible when navigating directly to the search hotel page after logout.");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Going To HomePage When Clicking On Login Again CTA")
    public void verifyGoingToHomePageByClickingOnLoginAgainCta() {
        logoutPage
                .clickToLoginAgainCTA();

        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), homePageURL + "index.php"),
                "Failed to navigate back to the Home/Login page after clicking 'Click here to login again'.");
    }
}