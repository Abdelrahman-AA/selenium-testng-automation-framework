package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookConfirmPage extends BasePage{

    //<editor-fold desc="Variables">
    private static final String VALUE_ATTRIBUTE = "value";
    //</editor-fold>

    //<editor-fold desc="Page Objects">
    public final StaticBarAtLoggedPages staticBar;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BookConfirmPage(WebDriver driver) {
        super(driver);
        this.staticBar = new StaticBarAtLoggedPages(driver);
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
        return eActions.getElementDomPropertyValue(hotelNameFixedField, VALUE_ATTRIBUTE);
    }
    public String getLocationFixedFieldText() {
        return eActions.getElementDomPropertyValue(locationFixedField, VALUE_ATTRIBUTE);
    }

    public String getRoomTypeFixedFieldText() {
        return eActions.getElementDomPropertyValue(roomTypeFixedField, VALUE_ATTRIBUTE);
    }

    public String getArrivalDateFixedField() {
        return eActions.getElementDomPropertyValue(arrivalDateFixedField, VALUE_ATTRIBUTE);
    }

    public String getDepartureDateFixedField() {
        return eActions.getElementDomPropertyValue(departureDateFixedField, VALUE_ATTRIBUTE);
    }

    public String getTotalRoomsFixedFieldText() {
        return eActions.getElementDomPropertyValue(totalRoomsFixedField, VALUE_ATTRIBUTE);
    }

    public String getAdultsPerRoomFixedFieldText() {
        return eActions.getElementDomPropertyValue(adultsPerRoomFixedField, VALUE_ATTRIBUTE);
    }

    public String getChildrenPerRoomFixedFieldText() {
        return eActions.getElementDomPropertyValue(childrenPerRoomFixedField, VALUE_ATTRIBUTE);
    }

    public String getPricePerNightFixedFieldText() {
        return eActions.getElementDomPropertyValue(pricePerNightFixedField, VALUE_ATTRIBUTE);
    }

    public String getTotalPriceFixedFieldText() {
        return eActions.getElementDomPropertyValue(totalPriceFixedField, VALUE_ATTRIBUTE);
    }

    public String getGstFixedFieldText() {
        return eActions.getElementDomPropertyValue(gstFixedField, VALUE_ATTRIBUTE);
    }

    public String getFinalBillingPriceFixedFieldText() {
        return eActions.getElementDomPropertyValue(finalBillingPriceFixedField, VALUE_ATTRIBUTE);
    }

    public String getFirstNameFixedFieldText() {
        return eActions.getElementDomPropertyValue(firstNameFixedField, VALUE_ATTRIBUTE);
    }

    public String getLastNameFixedFieldText() {
        return eActions.getElementDomPropertyValue(lastNameFixedField, VALUE_ATTRIBUTE);
    }

    public String getBillingAddressFixedFieldText() {
        return eActions.getElementDomPropertyValue(billingAddressFixedField, VALUE_ATTRIBUTE);
    }

    public String getOrderNoFixedFieldText() {
        return eActions.getElementDomPropertyValue(orderNoFixedField, VALUE_ATTRIBUTE);
    }
    //</editor-fold>

    //<editor-fold desc="Validations & Errors">
    public boolean isBookConfirmMsgVisible() {
        return eActions.checkElementVisibility(bookConfirmPageMsg);
    }
    //</editor-fold>
}
