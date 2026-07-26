package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StaticBarAtLoggedPages extends BasePage {

    //<editor-fold desc="Constructor">
    public StaticBarAtLoggedPages(WebDriver driver) {
        super(driver);
    }
    //</editor-fold>


    //<editor-fold desc="Locators">
    private final By helloUserName = By.id("username_show");
    private final By welcomeMsg = By.xpath("//td[normalize-space()='Welcome to Adactin Group of Hotels']");
    private final By searchHotelCta = By.xpath("//a[normalize-space()='Search Hotel']");
    private final By bookedItineraryCta = By.xpath("//a[normalize-space()='Booked Itinerary']");
    private final By changePasswordCta = By.xpath("//a[normalize-space()='Change Password']");
    private final By logoutCta = By.xpath("//a[normalize-space()='Logout']");
    //</editor-fold>


    //<editor-fold desc="Actions">
    public SearchHotelPage clickSearchHotelCTA() {
        eActions.clickWebElement(searchHotelCta);
        return new SearchHotelPage(driver);
    }

    public BookedItineraryPage clickBookedItineraryCTA() {
        eActions.clickWebElement(bookedItineraryCta);
        return new BookedItineraryPage(driver);
    }

    public ChangePasswordPage clickChangePasswordCTA() {
        eActions.clickWebElement(changePasswordCta);
        return new ChangePasswordPage(driver);
    }

    public LogoutPage clickLogoutCTA() {
        eActions.clickWebElement(logoutCta);
        return new LogoutPage(driver);
    }
    //</editor-fold>


    //<editor-fold desc="Validations & Errors">
    public boolean isStaticBarVisible() {
        return eActions.checkElementVisibility(helloUserName);
    }

    public boolean isStaticBarElementsVisible() {
        return eActions.checkElementVisibility(helloUserName)
                && eActions.checkElementVisibility(welcomeMsg)
                && eActions.checkElementVisibility(searchHotelCta)
                && eActions.checkElementVisibility(bookedItineraryCta)
                && eActions.checkElementVisibility(changePasswordCta)
                && eActions.checkElementVisibility(logoutCta);
    }

    public boolean isUserNameVisibleAtStaticBar(String userName) {
        String actualText = eActions.getElementText(helloUserName);
        return actualText != null && actualText.contains(userName);
    }
    //</editor-fold>
}