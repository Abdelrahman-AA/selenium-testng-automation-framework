package utils.helpers;

import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.StaticBarAtLoggedPages;

public class NewBrowserSessions {

    private NewBrowserSessions() {
    }

    public static boolean canLoginInNewSession(String username, String password, String homePageURL, String browser) {
        WebDriver secondaryDriver = null;
        boolean isLoginSuccessful = false;

        try {
            secondaryDriver = DriverFactory.initDriver(browser);
            HomePage secondaryHomePage = new HomePage(secondaryDriver);
            StaticBarAtLoggedPages secondaryStaticBar = new StaticBarAtLoggedPages(secondaryDriver);

            secondaryHomePage.openHomePageURL(homePageURL);
            secondaryHomePage.fillLoginFormAndSubmit(username, password);

            isLoginSuccessful = secondaryStaticBar.isStaticBarVisible();
        } catch (Exception e) {
            isLoginSuccessful = false;
        } finally {
            if (secondaryDriver != null) {
                secondaryDriver.quit();
            }
        }
        return isLoginSuccessful;
    }

    public static boolean canLoginInNewSession(String username, String password, String homePageURL) {
        String defaultBrowser = System.getProperty("browser", "chrome");
        return canLoginInNewSession(username, password, homePageURL, defaultBrowser);
    }
}