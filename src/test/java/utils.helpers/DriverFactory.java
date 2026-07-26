package utils.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver initDriver(String browser) {
        WebDriver driver;
        String targetBrowser = (browser == null) ? "chrome" : browser.trim().toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        switch (targetBrowser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) firefoxOptions.addArguments("-headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) edgeOptions.addArguments("--headless=new");
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                if (isHeadless) chromeOptions.addArguments("--headless=new");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().window().maximize();
        return driver;
    }
}