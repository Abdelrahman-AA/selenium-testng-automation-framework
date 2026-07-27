package utils.helpers;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Helpers {

    private static final String ALPHANUMERIC_CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890";
    private static final int DEFAULT_TIMEOUT_SECONDS = 5;

    private Helpers() {
    }

    public static String getRandomString(int length) {
        if (length <= 0) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current().nextInt(ALPHANUMERIC_CHARACTERS.length());
            stringBuilder.append(ALPHANUMERIC_CHARACTERS.charAt(index));
        }
        return stringBuilder.toString();
    }

    public static boolean isCurrentUrlEqualTo(WebDriver driver, String expectedUrl, int timeoutInSeconds) {
        if (driver == null || expectedUrl == null) {
            return false;
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return Boolean.TRUE.equals(wait.until(ExpectedConditions.urlToBe(expectedUrl)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static boolean isCurrentUrlEqualTo(WebDriver driver, String expectedUrl) {
        return isCurrentUrlEqualTo(driver, expectedUrl, DEFAULT_TIMEOUT_SECONDS);
    }

    public static String switchToTheNewWindowAtSameSession(WebDriver driver){
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        return originalWindow;
    }

    public static String getTodayDate(Locale locale) {
        return LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale));
    }

    public static String getDateOffsetFromToday(long days, Locale locale) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale));
    }

    public static Long getDifferenceBetweenTwoDatesByDays(String firstDate,String secondDate,Locale locale){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale);
        return   ChronoUnit.DAYS.between(LocalDate.parse(firstDate, formatter), LocalDate.parse(secondDate, formatter));
    }

    public static void getBack(WebDriver driver){
        driver.navigate().back();
    }

    public static void navigateToURL(WebDriver driver,String url){
        driver.navigate().to(url);
    }
}