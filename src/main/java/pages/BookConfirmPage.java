package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookConfirmPage extends BasePage{

    //<editor-fold desc="Variables">
    private static final String valueAttribute = "value";
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BookConfirmPage(WebDriver driver) {
        super(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Locators">
    private final By bookConfirmPageMsg = By.xpath("//td[normalize-space()='Booking Confirmation']");
    private final By hotelNameFixedField = By.id("hotel_name");
    private final By locationFixedField = By.id("location");
    private final By roomTypeFixedField = By.id("room_type");
    private final By arrivalDateFixedField = By.id("arrival_date");
    private final By departureDateFixedField = By.id("departure_text");
    private final By totalRoomsFixedField = By.id("total_rooms");
    private final By adultsPerRoomFixedField = By.id("adults_room");
    private final By childrenPerRoomFixedField = By.id("children_room");
    private final By pricePerNightFixedField = By.id("price_night");
    private final By totalPriceFixedField = By.id("total_price");
    private final By gstFixedField = By.id("gst");
    private final By finalBillingPriceFixedField = By.id("final_price");
    private final By firstNameFixedField = By.id("first_name");
    private final By lastNameFixedField = By.id("last_name");
    private final By billingAddressFixedField = By.id("address");
    private final By orderNoFixedField = By.id("order_no");
    private final By searchHotelButton = By.id("search_hotel");
    private final By myItineraryButton=By.id("my_itinerary");
    private final By logoutButton = By.id("logout");
    //</editor-fold>

    //<editor-fold desc="Actions">
    public SearchHotelPage clickSearchHotelButton(){
        eActions.clickWebElement(searchHotelButton);
        return new SearchHotelPage(driver);
    }

    public BookedItineraryPage clickMyItineraryButton(){
        eActions.clickWebElement(myItineraryButton);
        return new BookedItineraryPage(driver);
    }

    public LogoutPage clickLogoutButton(){
        eActions.clickWebElement(logoutButton);
        return new LogoutPage(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getHotelNameFixedFieldText() {
        return eActions.getElementDomPropertyValue(hotelNameFixedField, valueAttribute);
    }
    public String getLocationFixedFieldText() {
        return eActions.getElementDomPropertyValue(locationFixedField, valueAttribute);
    }

    public String getRoomTypeFixedFieldText() {
        return eActions.getElementDomPropertyValue(roomTypeFixedField, valueAttribute);
    }

    public String getArrivalDateFixedField() {
        return eActions.getElementDomPropertyValue(arrivalDateFixedField, valueAttribute);
    }

    public String getDepartureDateFixedField() {
        return eActions.getElementDomPropertyValue(departureDateFixedField, valueAttribute);
    }

    public String getTotalRoomsFixedFieldText() {
        return eActions.getElementDomPropertyValue(totalRoomsFixedField, valueAttribute);
    }

    public String getAdultsPerRoomFixedFieldText() {
        return eActions.getElementDomPropertyValue(adultsPerRoomFixedField, valueAttribute);
    }

    public String getChildrenPerRoomFixedFieldText() {
        return eActions.getElementDomPropertyValue(totalRoomsFixedField, valueAttribute);
    }

    public String getTotalDaysFixedFieldText() {
        return eActions.getElementDomPropertyValue(childrenPerRoomFixedField, valueAttribute);
    }

    public String getPricePerNightFixedFieldText() {
        return eActions.getElementDomPropertyValue(pricePerNightFixedField, valueAttribute);
    }

    public String getTotalPriceFixedFieldText() {
        return eActions.getElementDomPropertyValue(totalPriceFixedField, valueAttribute);
    }

    public String getGstFixedFieldText() {
        return eActions.getElementDomPropertyValue(gstFixedField, valueAttribute);
    }

    public String getFinalBillingPriceFixedFieldText() {
        return eActions.getElementDomPropertyValue(finalBillingPriceFixedField, valueAttribute);
    }

    public String getFirstNameFixedFieldText() {
        return eActions.getElementDomPropertyValue(firstNameFixedField, valueAttribute);
    }

    public String getLastNameFixedFieldText() {
        return eActions.getElementDomPropertyValue(lastNameFixedField, valueAttribute);
    }

    public String getBillingAddressFixedFieldText() {
        return eActions.getElementDomPropertyValue(billingAddressFixedField, valueAttribute);
    }

    public String getOrderNoFixedFieldText() {
        return eActions.getElementDomPropertyValue(orderNoFixedField, valueAttribute);
    }
    //</editor-fold>

    //<editor-fold desc="Validations & Errors">
    public boolean isBookConfirmMsgVisible() {
        return eActions.checkElementVisibility(bookConfirmPageMsg);
    }
    //</editor-fold>
}
