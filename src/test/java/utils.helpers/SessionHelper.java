package utils.helpers;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class SessionHelper {

    private SessionHelper() {
    }

    public static String getCookieValue(WebDriver driver, String cookieName) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver instance cannot be null!");
        }

        Cookie cookie = driver.manage().getCookieNamed(cookieName);
        return (cookie != null) ? cookie.getValue() : null;
    }

    public static String getPhpSessionId(WebDriver driver) {
        return getCookieValue(driver, "PHPSESSID");
    }
}