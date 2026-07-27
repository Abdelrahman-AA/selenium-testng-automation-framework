package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static utils.helpers.Helpers.isCurrentUrlEqualTo;

public class StaticBarAtLoggedPagesTest extends BaseTest {

    @BeforeMethod
    public void initializePage() {
        searchHotelPage = homePage
                .openHomePageAndLoginWithValidRegisteredCredentials(registeredUserName, registeredPassword, homePageURL);
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Static Bar To Be Visible")
    public void verifyStaticBarVisibility() {
        Assert.assertTrue(searchHotelPage.staticBar.isStaticBarVisible(), "Static bar is not visible on Search Hotel page.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Static Bar Elements To Be Visible")
    public void verifyStaticBarElementsVisibility() {
        Assert.assertTrue(searchHotelPage.staticBar.isStaticBarElementsVisible(), "Static bar elements are not displayed properly.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Going To Booked Itinerary Page From Static Bar CTA And Static Bar Is Still Visible")
    public void verifyGoingToBookedItineraryPageFromStaticBarCtaAndStaticBarIsStillVisible() {
        bookedItineraryPage = searchHotelPage
                .staticBar
                .clickBookedItineraryCTA();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), bookedItineraryURL), "User was not redirected to Booked Itinerary page.");
        softAssert.assertTrue(bookedItineraryPage.staticBar.isStaticBarVisible(), "Static bar is not visible on Booked Itinerary page.");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Going To Change Password Page From Static Bar CTA And Static Bar Is Still Visible")
    public void verifyGoingToChangePasswordPageFromStaticBarCtaAndStaticBarIsStillVisible() {
        changePasswordPage = searchHotelPage
                .staticBar
                .clickChangePasswordCTA();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), changePasswordPageURL), "User was not redirected to Change Password page.");
        softAssert.assertTrue(changePasswordPage.staticBar.isStaticBarVisible(), "Static bar is not visible on Change Password page.");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Going To Search Hotel Page From Another Logged Page And Static Bar Is Still Visible")
    public void verifyGoingToSearchHotelPageFromAnotherLoggedPageAndStaticBarIsStillVisible() {
        changePasswordPage = searchHotelPage
                .staticBar
                .clickChangePasswordCTA();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), changePasswordPageURL), "Failed to navigate to Change Password page.");

        changePasswordPage
                .staticBar
                .clickSearchHotelCTA();

        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), searchHotelPageURL), "Failed to navigate back to Search Hotel page.");
        softAssert.assertTrue(searchHotelPage.staticBar.isStaticBarVisible(), "Static bar is not visible after returning to Search Hotel page.");
        softAssert.assertAll();
    }

    @Test(groups =  {"smoke", "happy-path"},
            description = "Should Going To Logged Out Page From Static Bar CTA And Static Bar Is Not Visible")
    public void verifyGoingToLoggedOutPageFromStaticBarCtaAndStaticBarIsNotVisible() {
        logoutPage = searchHotelPage
                .staticBar
                .clickLogoutCTA();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isCurrentUrlEqualTo(getDriver(), logoutURL), "User was not redirected to Logout page.");
        softAssert.assertFalse(searchHotelPage.staticBar.isStaticBarVisible(), "Static bar should not be visible after logging out.");
        softAssert.assertAll();
    }
}