package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookHotelPage extends BasePage {

    //<editor-fold desc="Variables">
    private static final String valueAttribute = "value";
    //</editor-fold>

    //<editor-fold desc="Page Objects">
    public StaticBarAtLoggedPages staticBar;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BookHotelPage(WebDriver driver) {
        super(driver);
        this.staticBar = new StaticBarAtLoggedPages(driver);
    }
    //</editor-fold>

    //<editor-fold desc="Locators">
    private final By bookHotelPageMsg = By.xpath("//td[normalize-space()='Book A Hotel']");
    private final By hotelNameFixedField = By.id("hotel_name_dis");
    private final By locationFixedField = By.id("location_dis");
    private final By roomTypeFixedField = By.id("room_type_dis");
    private final By numOfRoomsFixedField = By.id("room_num_dis");
    private final By totalDaysFixedField = By.id("total_days_dis");
    private final By pricePerNightFixedField = By.id("price_night_dis");
    private final By totalPriceFixedField = By.id("total_price_dis");

    private final By gstFixedField = By.id("gst_dis");
    private final By finalBilledPriceFixedField = By.id("final_price_dis");
    private final By firstNameField = By.id("first_name");
    private final By lastNameField = By.id("last_name");
    private final By billingAddressField = By.id("address");
    private final By creditCardNumField = By.id("cc_num");
    private final By creditCardTypeSelector = By.id("cc_type");
    private final By creditCardExpiryDateMonthSelector = By.id("cc_exp_month");
    private final By creditCardExpiryDateYearSelector = By.id("cc_exp_year");
    private final By creditCardCvvNumField = By.id("cc_cvv");

    private final By bookNowButton = By.id("book_now");
    private final By cancelButton = By.id("cancel");

    private final By firstNameFieldErrorMsg = By.id("first_name_span");
    private final By lastNameFieldErrorMsg = By.id("last_name_span");
    private final By billingAddressFieldErrorMsg = By.id("address_span");
    private final By creditCardNumFieldErrorMsg = By.id("cc_num_span");
    private final By creditCardTypeSelectorErrorMsg = By.id("cc_type_span");
    private final By creditCardExpiryDateSelectorsErrorMsg = By.id("cc_expiry_span");
    private final By creditCardCvvFieldErrorMsg = By.id("cc_cvv_span");
    //</editor-fold>

    //<editor-fold desc="Actions">
    public BookHotelPage enterFirstName(String firstName) {
        eActions.sendText(firstNameField, firstName);
        return this;
    }

    public BookHotelPage enterLastName(String lastName) {
        eActions.sendText(lastNameField, lastName);
        return this;
    }

    public BookHotelPage enterBillingAddress(String billingAddress) {
        eActions.sendText(billingAddressField, billingAddress);
        return this;
    }

    public BookHotelPage enterCreditCardNum(String creditCardNum) {
        eActions.sendText(creditCardNumField, creditCardNum);
        return this;
    }

    public BookHotelPage selectCreditCardTypeByIndex(int index) {
        eActions.dropDownSelectByIndex(creditCardTypeSelector, index);
        return this;
    }

    public BookHotelPage selectCreditCardExpiryDateMonthByIndex(int index) {
        eActions.dropDownSelectByIndex(creditCardExpiryDateMonthSelector, index);
        return this;
    }

    public BookHotelPage selectCreditCardExpiryDateYearByIndex(int index) {
        eActions.dropDownSelectByIndex(creditCardExpiryDateYearSelector, index);
        return this;
    }

    public BookHotelPage enterCreditCardCvvNum(String creditCardCvvNum) {
        eActions.sendText(creditCardCvvNumField, creditCardCvvNum);
        return this;
    }

    public BookConfirmPage clickBookNow() {
        eActions.clickWebElement(bookNowButton);
        return new BookConfirmPage(driver);
    }

    public SearchHotelPage clickCancel() {
        eActions.clickWebElement(cancelButton);
        return new SearchHotelPage(driver);
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

    public String getNumOfRoomsFixedFieldText() {
        return eActions.getElementDomPropertyValue(numOfRoomsFixedField, valueAttribute);
    }

    public String getTotalDaysFixedFieldText() {
        return eActions.getElementDomPropertyValue(totalDaysFixedField, valueAttribute);
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

    public String getFinalBilledPriceFixedFieldText() {
        return eActions.getElementDomPropertyValue(finalBilledPriceFixedField, valueAttribute);
    }

    public String getFirstNameFieldErrorMsg() {
        return eActions.getElementText(firstNameFieldErrorMsg);
    }

    public String getLastNameFieldErrorMsg() {
        return eActions.getElementText(lastNameFieldErrorMsg);
    }

    public String getBillingAddressFieldErrorMsg() {
        return eActions.getElementText(billingAddressFieldErrorMsg);
    }

    public String getCreditCardNumFieldErrorMsg() {
        return eActions.getElementText(creditCardNumFieldErrorMsg);
    }

    public String getCreditCardTypeSelectorErrorMsg() {
        return eActions.getElementText(creditCardTypeSelectorErrorMsg);
    }

    public String getCreditCardExpiryDateSelectorsErrorMsg() {
        return eActions.getElementText(creditCardExpiryDateSelectorsErrorMsg);
    }

    public String getCreditCardCvvFieldErrorMsg() {
        return eActions.getElementText(creditCardCvvFieldErrorMsg);
    }

    public int getLastOptionOfCreditCardTypeSelector() {

        return eActions.getDropDownNumOfSelections(creditCardTypeSelector) - 1;
    }

    public int getLastOptionOfCreditCardExpiryMonthSelector() {
        return eActions.getDropDownNumOfSelections(creditCardExpiryDateMonthSelector) - 1;
    }

    public int getLastOptionOfCreditCardExpiryYearSelector() {
        return eActions.getDropDownNumOfSelections(creditCardExpiryDateYearSelector) - 1;
    }

    public String getCreditCardExpiryMonthTextByIndex(int index){
        return eActions.getDropDownTextOfSelectionByIndex(creditCardExpiryDateMonthSelector,index);
    }

    public String getCreditCardExpiryYearTextByIndex(int index){
        return eActions.getDropDownTextOfSelectionByIndex(creditCardExpiryDateYearSelector,index);
    }

    public String getCreditCardTypeTextByIndex(int index){
        return eActions.getDropDownTextOfSelectionByIndex(creditCardTypeSelector,index);
    }
    //</editor-fold>

    //<editor-fold desc="Validations & Errors">
    public boolean isBookHotelMsgVisible() {
        return eActions.checkElementVisibility(bookHotelPageMsg);
    }

    public boolean isFirstNameFieldErrorMsgVisible() {
        return eActions.checkElementVisibility(firstNameFieldErrorMsg);
    }

    public boolean isLastNameFieldErrorMsgVisible() {
        return eActions.checkElementVisibility(lastNameFieldErrorMsg);
    }

    public boolean isBillingAddressFieldErrorMsgVisible() {
        return eActions.checkElementVisibility(billingAddressFieldErrorMsg);
    }

    public boolean isCreditCardNumFieldErrorMsgVisible() {
        return eActions.checkElementVisibility(creditCardNumFieldErrorMsg);
    }

    public boolean isCreditCardTypeSelectorErrorMsgVisible() {
        return eActions.checkElementVisibility(creditCardTypeSelectorErrorMsg);
    }

    public boolean isCreditCardExpiryDateSelectorsErrorMsgVisible() {
        return eActions.checkElementVisibility(creditCardExpiryDateSelectorsErrorMsg);
    }

    public boolean isCreditCardCvvFieldErrorMsgVisible() {
        return eActions.checkElementVisibility(creditCardCvvFieldErrorMsg);
    }
    //</editor-fold>

    //<editor-fold desc="Business Workflows">
    public BookConfirmPage fillBookHotelFormAndSubmit(
            String firstName,
            String lastName,
            String billingAddress,
            String creditCardNum,
            int creditCardTypeIndex,
            int creditCardExpiryMonthIndex,
            int creditCardExpiryYearIndex,
            String creditCardCvvNum) {

        return enterFirstName(firstName)
                .enterLastName(lastName)
                .enterBillingAddress(billingAddress)
                .enterCreditCardNum(creditCardNum)
                .selectCreditCardTypeByIndex(creditCardTypeIndex)
                .selectCreditCardExpiryDateMonthByIndex(creditCardExpiryMonthIndex)
                .selectCreditCardExpiryDateYearByIndex(creditCardExpiryYearIndex)
                .enterCreditCardCvvNum(creditCardCvvNum)
                .clickBookNow();
    }

    public SearchHotelPage fillBookHotelFormAndCancel(
            String firstName,
            String lastName,
            String billingAddress,
            String creditCardNum,
            int creditCardTypeIndex,
            int creditCardExpiryMonthIndex,
            int creditCardExpiryYearIndex,
            String creditCardCvvNum) {

        return enterFirstName(firstName)
                .enterLastName(lastName)
                .enterBillingAddress(billingAddress)
                .enterCreditCardNum(creditCardNum)
                .selectCreditCardTypeByIndex(creditCardTypeIndex)
                .selectCreditCardExpiryDateMonthByIndex(creditCardExpiryMonthIndex)
                .selectCreditCardExpiryDateYearByIndex(creditCardExpiryYearIndex)
                .enterCreditCardCvvNum(creditCardCvvNum)
                .clickCancel();
    }
    //</editor-fold>
}
