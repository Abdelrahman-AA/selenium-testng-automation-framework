package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class SearchHotelPage extends BasePage {

    //<editor-fold desc="Page Objects">
    public final StaticBarAtLoggedPages staticBar;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public SearchHotelPage(WebDriver driver) {
        super(driver);
        this.staticBar = new StaticBarAtLoggedPages(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Locators">
    private final By searchHotelPageMsg = By.cssSelector(".login_title");
    private final By locationSelector = By.id("location");
    private final By hotelsSelector = By.id("hotels");
    private final By roomTypeSelector = By.id("room_type");
    private final By numbersOfRoomsSelector = By.id("room_nos");
    private final By checkInDateField = By.id("datepick_in");
    private final By checkOutDateField = By.id("datepick_out");
    private final By adultPerRoomSelector = By.id("adult_room");
    private final By childrenPerRoomSelector = By.id("child_room");
    private final By searchButton = By.id("Submit");
    private final By resetButton = By.id("Reset");

    private final By locationErrorMsg = By.id("location_span");
    private final By checkInDateErrorMsg = By.id("checkin_span");
    private final By checkOutDateErrorMsg = By.id("checkout_span");
    //</editor-fold>

    //<editor-fold desc="Actions">
    public SearchHotelPage selectIndexOfLocation(int index) {
        eActions.dropDownSelectByIndex(locationSelector, index);
        return this;
    }

    public SearchHotelPage selectIndexOfHotel(int index) {
        eActions.dropDownSelectByIndex(hotelsSelector, index);
        return this;
    }

    public SearchHotelPage selectIndexOfRoomType(int index) {
        eActions.dropDownSelectByIndex(roomTypeSelector, index);
        return this;
    }

    public SearchHotelPage selectIndexOfNumsOfRoom(int index) {
        eActions.dropDownSelectByIndex(numbersOfRoomsSelector, index);
        return this;
    }

    public SearchHotelPage selectIndexOfAdultsPerRoom(int index) {
        eActions.dropDownSelectByIndex(adultPerRoomSelector, index);
        return this;
    }

    public SearchHotelPage selectIndexOfChildrenPerRoom(int index) {
        eActions.dropDownSelectByIndex(childrenPerRoomSelector, index);
        return this;
    }

    public SearchHotelPage enterCheckInDate(String date) {
        eActions.sendText(checkInDateField, date);
        return this;
    }

    public SearchHotelPage enterCheckOutDate(String date) {
        eActions.sendText(checkOutDateField, date);
        return this;
    }

    public SelectHotelPage clickSearch() {
        eActions.clickWebElement(searchButton);
        return new SelectHotelPage(driver);
    }

    public SearchHotelPage clickReset() {
        eActions.clickWebElement(resetButton);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getDefaultOfLocations() {
        return eActions.getDropDownDefaultOptionText(locationSelector);
    }

    public int getNumOfLocations() {
        return eActions.getDropDownNumOfSelections(locationSelector);
    }

    public String getLocationNameByIndex(int index) {
        return eActions.getDropDownTextOfSelectionByIndex(locationSelector, index);
    }

    public String geDefaultOfHotels() {
        return eActions.getDropDownDefaultOptionText(hotelsSelector);
    }

    public int getNumOfHotels() {
        return eActions.getDropDownNumOfSelections(hotelsSelector);
    }

    public String getHotelNameByIndex(int index) {
        return eActions.getDropDownTextOfSelectionByIndex(hotelsSelector, index);
    }

    public String getDefaultOfRoomTypes() {
        return eActions.getDropDownDefaultOptionText(roomTypeSelector);
    }

    public int getNumOfRoomTypes() {
        return eActions.getDropDownNumOfSelections(roomTypeSelector);
    }

    public String getRoomTypeNameByIndex(int index) {
        return eActions.getDropDownTextOfSelectionByIndex(roomTypeSelector, index);
    }

    public String getDefaultOfNumsOfRooms() {
        return eActions.getDropDownDefaultOptionText(numbersOfRoomsSelector);
    }

    public int getNumOfNumsOfRooms() {
        return eActions.getDropDownNumOfSelections(numbersOfRoomsSelector);
    }

    public String getNumOfRoomNameByIndex(int index) {
        return eActions.getDropDownTextOfSelectionByIndex(numbersOfRoomsSelector, index);
    }

    public String getDefaultOfAdultsPerRoom() {
        return eActions.getDropDownDefaultOptionText(adultPerRoomSelector);
    }

    public int getNumOfAdultsPerRoom() {
        return eActions.getDropDownNumOfSelections(adultPerRoomSelector);
    }

    public String getAdultsPerRoomNameByIndex(int index) {
        return eActions.getDropDownTextOfSelectionByIndex(adultPerRoomSelector, index);
    }

    public String getDefaultOfChildrenPerRoom() {
        return eActions.getDropDownDefaultOptionText(childrenPerRoomSelector);
    }

    public int getNumOfChildrenPerRoom() {
        return eActions.getDropDownNumOfSelections(childrenPerRoomSelector);
    }

    public String getChildrenPerRoomNameByIndex(int index) {
        return eActions.getDropDownTextOfSelectionByIndex(childrenPerRoomSelector, index);
    }

    public String getCurrentCheckInFieldText() {
        return eActions.getElementDomPropertyValue(checkInDateField, "value");
    }

    public String getCurrentCheckOutFieldText() {
        return eActions.getElementDomPropertyValue(checkOutDateField, "value");
    }

    public String getLocationErrorMsgText() {
        return eActions.getElementText(locationErrorMsg);
    }

    public String getCheckInDateErrorMsgText() {
        return eActions.getElementText(checkInDateErrorMsg);
    }

    public String getCheckOutDateErrorMsgText() {
        return eActions.getElementText(checkOutDateErrorMsg);
    }

    public List<String> getTestedDataForSave(
            int locationIndex, int hotelIndex, int roomTypeIndex,
            int numOfRoomsIndex, int adultsPerRoomIndex, int childrenPerRoomIndex) {

        return List.of(
                getLocationNameByIndex(locationIndex),
                getHotelNameByIndex(hotelIndex),
                getRoomTypeNameByIndex(roomTypeIndex),
                getNumOfRoomNameByIndex(numOfRoomsIndex),
                getAdultsPerRoomNameByIndex(adultsPerRoomIndex),
                getChildrenPerRoomNameByIndex(childrenPerRoomIndex)
        );
    }
    //</editor-fold>

    //<editor-fold desc="Validations & Errors">
    public boolean isSearchHotelPageMsgVisible() {
        return eActions.checkElementVisibility(searchHotelPageMsg);
    }

    public boolean isSearchFormVisible() {
        return eActions.checkElementVisibility(locationSelector) && eActions.checkElementVisibility(searchButton);
    }

    public boolean isLocationErrorMsgVisible() {
        return eActions.checkElementVisibility(locationErrorMsg);
    }

    public boolean isCheckInDateErrorMsgVisible() {
        return eActions.checkElementVisibility(checkInDateErrorMsg);
    }

    public boolean isCheckOutDateErrorMsgVisible() {
        return eActions.checkElementVisibility(checkOutDateErrorMsg);
    }

    public boolean isDropDownLocationSelected(String location) {
        return location.equals(eActions.getDropDownSelectedOptionText(locationSelector));
    }

    public boolean isDropDownHotelSelected(String hotel) {
        return hotel.equals(eActions.getDropDownSelectedOptionText(hotelsSelector));
    }

    public boolean isDropDownRoomTypeSelected(String roomType) {
        return roomType.equals(eActions.getDropDownSelectedOptionText(roomTypeSelector));
    }

    public boolean isDropDownNumbersOfRoomsSelected(String numbersOfRooms) {
        return numbersOfRooms.equals(eActions.getDropDownSelectedOptionText(numbersOfRoomsSelector));
    }

    public boolean isDropDownAdultPerRoomSelected(String adultPerRoom) {
        return adultPerRoom.equals(eActions.getDropDownSelectedOptionText(adultPerRoomSelector));
    }

    public boolean isDropDownChildrenPerRoomSelected(String childrenPerRoom) {
        return childrenPerRoom.equals(eActions.getDropDownSelectedOptionText(childrenPerRoomSelector));
    }
    //</editor-fold>

    //<editor-fold desc="Business Workflows">
    public SelectHotelPage selectAndFillAllFieldsAndClickSearch(
            int locationIndex,
            int hotelIndex,
            int roomTypeIndex,
            int numOfRoomsIndex,
            String checkInDate,
            String checkOutDate,
            int adultsPerRoomIndex,
            int childrenPerRoomIndex) {

      return   selectIndexOfLocation(locationIndex)
                .selectIndexOfHotel(hotelIndex)
                .selectIndexOfRoomType(roomTypeIndex)
                .selectIndexOfNumsOfRoom(numOfRoomsIndex)
                .enterCheckInDate(checkInDate)
                .enterCheckOutDate(checkOutDate)
                .selectIndexOfAdultsPerRoom(adultsPerRoomIndex)
                .selectIndexOfChildrenPerRoom(childrenPerRoomIndex)
                .clickSearch();
    }

    public SearchHotelPage selectAndFillAllFields(
            int locationIndex,
            int hotelIndex,
            int roomTypeIndex,
            int numOfRoomsIndex,
            String checkInDate,
            String checkOutDate,
            int adultsPerRoomIndex,
            int childrenPerRoomIndex) {

       return selectIndexOfLocation(locationIndex)
                .selectIndexOfHotel(hotelIndex)
                .selectIndexOfRoomType(roomTypeIndex)
                .selectIndexOfNumsOfRoom(numOfRoomsIndex)
                .enterCheckInDate(checkInDate)
                .enterCheckOutDate(checkOutDate)
                .selectIndexOfAdultsPerRoom(adultsPerRoomIndex)
                .selectIndexOfChildrenPerRoom(childrenPerRoomIndex);
    }
    //</editor-fold>
}