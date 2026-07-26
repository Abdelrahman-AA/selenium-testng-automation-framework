package pages.Test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.*;
import testData.TestData;
import utils.helpers.DriverFactory;

import java.util.Locale;

import static utils.helpers.Helpers.getDateOffsetFromToday;
import static utils.helpers.Helpers.getTodayDate;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    //Page Objects
    protected HomePage homePage;
    protected RegisterPage registerPage;
    protected ForgetPasswordPage forgetPasswordPage;
    protected ChangePasswordPage changePasswordPage;
    protected SearchHotelPage searchHotelPage;
    protected SelectHotelPage selectHotelPage;
    protected BookHotelPage bookHotelPage;
    protected BookConfirmPage bookConfirmPage;
    protected BookedItineraryPage bookedItineraryPage;
    protected LogoutPage logoutPage;

    protected final String registeredUserName = TestData.get("TestData.ValidRegisteredAccount.UserName");
    protected final String registeredPassword = TestData.get("TestData.ValidRegisteredAccount.Password");
    protected final String empty = "";
    protected final String testedArrivalDate = getTodayDate(Locale.UK);
    protected final String testedDepartureDate = getDateOffsetFromToday(1, Locale.UK);


    //URLs from YAML TestData
    protected final String homePageURL = TestData.get("Pages.HomePage.URL");
    protected final String searchHotelPageURL = TestData.get("Pages.SearchHotelPage.URL");
    protected final String selectHotelPageURL = TestData.get("Pages.SelectHotelPage.URL");
    protected final String bookHotelPageURL = TestData.get("Pages.BookHotelPage.URL");
    protected final String bookConfirmPageURL = TestData.get("Pages.BookConfirm.URL");
    protected final String bookedItineraryURL = TestData.get("Pages.BookedItinerary.URL");
    protected final String registerPageURL = TestData.get("Pages.RegisterPage.URL");
    protected final String forgetPasswordURL = TestData.get("Pages.ForgetPassword.URL");
    protected final String changePasswordPageURL = TestData.get("Pages.ChangePasswordPage.URL");
    protected final String logoutURL = TestData.get("Pages.Logout.URL");
    protected final String termsConditionsURL = TestData.get("StaticURLs.TermsConditions");

    protected WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    protected final int threadPoolSize=4;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        WebDriver driver = DriverFactory.initDriver(browser);
        driverThreadLocal.set(driver);
        homePage = new HomePage(getDriver());
    }

    @AfterMethod
    public void tearDown() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}