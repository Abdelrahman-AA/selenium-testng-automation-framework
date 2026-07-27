package pages.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testData.TestData;

import java.util.List;
import java.util.Locale;

import static utils.helpers.Helpers.*;

public class BookHotelPageTest extends BaseTest {

    private List<String> testedData;

    @BeforeMethod
    public void initializePage() {
        searchHotelPage = homePage
                .openHomePageAndLoginWithValidRegisteredCredentials(registeredUserName, registeredPassword, homePageURL);

        int lastLocation = searchHotelPage.getNumOfLocations() - 1;
        int lastHotel = searchHotelPage.getNumOfHotels() - 1;
        int lastRoomType = searchHotelPage.getNumOfRoomTypes() - 1;
        int lastNumOfRooms = searchHotelPage.getNumOfNumsOfRooms() - 1;
        int lastAdults = searchHotelPage.getNumOfAdultsPerRoom() - 1;
        int lastChildren = searchHotelPage.getNumOfChildrenPerRoom() - 1;

        testedData = searchHotelPage.getTestedDataForSave(
                lastLocation, lastHotel, lastRoomType, lastNumOfRooms, lastAdults, lastChildren
        );

        selectHotelPage = searchHotelPage.selectAndFillAllFieldsAndClickSearch(
                lastLocation,
                lastHotel,
                lastRoomType,
                lastNumOfRooms,
                testedArrivalDate,
                testedDepartureDate,
                lastAdults,
                lastChildren
        );

        int tableRowSelection = selectHotelPage.getResultsTableRowCount() - 1;
        testedData.add(selectHotelPage.getTableCellValueByRowAndColumn(tableRowSelection, 8));
        testedData.add(selectHotelPage.getTableCellValueByRowAndColumn(tableRowSelection, 9));

        bookHotelPage = selectHotelPage.selectHotelFromTableAndClickContinueByIndex(tableRowSelection);
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Return To Search Page When Click Cancel",
            threadPoolSize = threadPoolSize)
    public void verifyReturnToSearchPageWhenClickCancel() {
        bookHotelPage
                .clickCancel();
        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), selectHotelPageURL),
                "Failed to return to the Select Hotel page after clicking cancel.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Hotel Name Against Selected",
            threadPoolSize = threadPoolSize)
    public void verifyHotelNameAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getHotelNameFixedFieldText(), testedData.get(1),
                "Hotel name does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Location Against Selected",
            threadPoolSize = threadPoolSize)
    public void verifyLocationAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getLocationFixedFieldText(), testedData.get(0),
                "Location does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Room Type Against Selected",
            threadPoolSize = threadPoolSize)
    public void verifyRoomTypeAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getRoomTypeFixedFieldText(), testedData.get(2),
                "Room type does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Num Of Rooms Against Selected",
            threadPoolSize = threadPoolSize)
    public void verifyNumOfRoomsAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getNumOfRoomsFixedFieldText().split(" ")[0].trim(), testedData.get(3).split(" ")[0].trim(),
                "Number of rooms does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Total Days Against Selected",
            threadPoolSize = threadPoolSize)
    public void verifyTotalDaysAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getTotalDaysFixedFieldText().split(" ")[0].trim(), getDifferenceBetweenTwoDatesByDays(testedArrivalDate, testedDepartureDate, Locale.UK).toString(),
                "Total days do not match the selected dates range.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Price Per Night Against Selected",
            threadPoolSize = threadPoolSize)
    public void verifyPricePerNightAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getPricePerNightFixedFieldText().split(" ")[2].trim(), testedData.get(6).split(" ")[2].trim(),
                "Price per night does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Total Price Before Gst Against Selected",
            threadPoolSize = threadPoolSize)
    public void verifyTotalPriceBeforeGstAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getTotalPriceFixedFieldText().split(" ")[2].trim(), testedData.get(7).split(" ")[2].trim(),
                "Total price before GST does not match the selected one.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Gst As Ten Percent",
            threadPoolSize = threadPoolSize)
    public void verifyGstAsTenPercent() {
        Assert.assertEquals(Integer.valueOf(bookHotelPage.getGstFixedFieldText().split(" ")[2].trim()),
                Integer.valueOf(bookHotelPage.getTotalPriceFixedFieldText().split(" ")[2].trim()) / 10,
                "GST is not calculated correctly as 10% of total price.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Verify Final Billing Price Is Summation Gst And Total Price",
            threadPoolSize = threadPoolSize)
    public void verifyFinalBillingPriceIsSummationGstAndTotalPrice() {
        Assert.assertEquals(Integer.valueOf(bookHotelPage.getFinalBilledPriceFixedFieldText().split(" ")[2].trim()),
                Integer.valueOf(bookHotelPage.getTotalPriceFixedFieldText().split(" ")[2].trim())
                        + Integer.valueOf(bookHotelPage.getGstFixedFieldText().split(" ")[2].trim()),
                "Final billed price does not equal the sum of total price and GST.");
    }

    @Test(groups = {"smoke", "happy-path"},
            description = "Should Able To Book Hotel With Valid Data",
            threadPoolSize = threadPoolSize)
    public void verifyBookingWithValidData() {
        bookHotelPage
                .enterFirstName(TestData.get("TestData.BillingData.Valid.FirstName"))
                .enterLastName(TestData.get("TestData.BillingData.Valid.LastName"))
                .enterBillingAddress(TestData.get("TestData.BillingData.Valid.BillingAddress"))
                .enterCreditCardNum(TestData.get("TestData.BillingData.Valid.CreditCardNum"))
                .selectCreditCardTypeByIndex(bookHotelPage.getLastOptionOfCreditCardTypeSelector())
                .selectCreditCardExpiryDateMonthByIndex(bookHotelPage.getLastOptionOfCreditCardExpiryMonthSelector())
                .selectCreditCardExpiryDateYearByIndex(bookHotelPage.getLastOptionOfCreditCardExpiryYearSelector())
                .enterCreditCardCvvNum(TestData.get("TestData.BillingData.Valid.CreditCardNum"))
                .clickBookNow();
        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), bookConfirmPageURL),
                "User was not redirected to the Book Confirmation page after valid booking.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify First Name Can Not Contain Numbers",
            threadPoolSize = threadPoolSize)
    public void verifyFirstNameCantContainNum() {
        bookHotelPage
                .enterFirstName(TestData.get("TestData.BillingData.InValidFormat.FirstName"))
                .clickBookNow();

        Assert.assertTrue(bookHotelPage.isFirstNameFieldErrorMsgVisible(),
                "First name error message should be visible when containing numbers.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify First Name Can Not Be Empty",
            threadPoolSize = threadPoolSize)
    public void verifyFirstNameCantBeEmpty() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isFirstNameFieldErrorMsgVisible(),
                "First name error message should be visible when empty.");
        softAssert.assertEquals(bookHotelPage.getFirstNameFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.EmptyFirstName"),
                "First name error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Last Name Can Not Contain Numbers",
            threadPoolSize = threadPoolSize)
    public void verifyLastNameCantContainNum() {
        bookHotelPage
                .enterLastName(TestData.get("TestData.BillingData.InValidFormat.LastName"))
                .clickBookNow();

        Assert.assertTrue(bookHotelPage.isLastNameFieldErrorMsgVisible(),
                "Last name error message should be visible when containing numbers.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Last Name Can Not Be Empty",
            threadPoolSize = threadPoolSize)
    public void verifyLastNameCantBeEmpty() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isLastNameFieldErrorMsgVisible(),
                "Last name error message should be visible when empty.");
        softAssert.assertEquals(bookHotelPage.getLastNameFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.EmptyLastName"),
                "Last name error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Credit Card Num Can Not Contain Characters",
            threadPoolSize = threadPoolSize)
    public void verifyCCNumCantContainCharacters() {
        bookHotelPage
                .enterCreditCardNum(TestData.get("TestData.BillingData.InValidFormat.CreditCardNum"))
                .clickBookNow();

        Assert.assertTrue(bookHotelPage.isLastNameFieldErrorMsgVisible(),
                "Credit card number error message should be visible when containing characters.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Credit Card Num Can Not Be Less Than Sixteen Digits",
            threadPoolSize = threadPoolSize)
    public void verifyCCNumCantBeLessThanSixteenDigits() {
        bookHotelPage
                .enterCreditCardNum(TestData.get("TestData.BillingData.InValidShort.CreditCardNum"))
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardNumFieldErrorMsgVisible(),
                "Credit card number error message should be visible for short numbers.");
        softAssert.assertEquals(bookHotelPage.getCreditCardNumFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.ErrorCreditCard"),
                "Credit card error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Credit Card Num Can Not Be Empty",
            threadPoolSize = threadPoolSize)
    public void verifyCCNumCantBeEmpty() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardNumFieldErrorMsgVisible(),
                "Credit card number error message should be visible when empty.");
        softAssert.assertEquals(bookHotelPage.getCreditCardNumFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.ErrorCreditCard"),
                "Credit card error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Error Msg For Not Selected CC Type",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForNotSelectedCCType() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardTypeSelectorErrorMsgVisible(),
                "Credit card type error message should be visible when not selected.");
        softAssert.assertEquals(bookHotelPage.getCreditCardTypeSelectorErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.CreditCardTypeNotSelected"),
                "Credit card type error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Error Msg For Not Selected Expiry Month",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForNotSelectedExpiryMonth() {
        bookHotelPage
                .selectCreditCardExpiryDateYearByIndex(bookHotelPage.getLastOptionOfCreditCardExpiryYearSelector())
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardExpiryDateSelectorsErrorMsgVisible(),
                "Expiry date error message should be visible when month is not selected.");
        softAssert.assertEquals(bookHotelPage.getCreditCardExpiryDateSelectorsErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.CreditCardExpiryDateNotSelectedMonth"),
                "Expiry date error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Error Msg For Not Selected Expiry Year",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForNotSelectedExpiryYear() {
        bookHotelPage
                .selectCreditCardExpiryDateMonthByIndex(bookHotelPage.getLastOptionOfCreditCardExpiryMonthSelector())
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardExpiryDateSelectorsErrorMsgVisible(),
                "Expiry date error message should be visible when year is not selected.");
        softAssert.assertEquals(bookHotelPage.getCreditCardExpiryDateSelectorsErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.CreditCardExpiryDateNotSelectedYear"),
                "Expiry date error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Error Msg For Not Selected Expiry Month And Year",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgForNotSelectedExpiryMonthAndYear() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardExpiryDateSelectorsErrorMsgVisible(),
                "Expiry date error message should be visible when both month and year are not selected.");
        softAssert.assertEquals(bookHotelPage.getCreditCardExpiryDateSelectorsErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.CreditCardExpiryDateNotSelectedMonth"),
                "Expiry date error message text mismatch.");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify Error Msg Visibility When CC Cvv Has InValid Short Data",
            threadPoolSize = threadPoolSize)
    public void verifyErrorMsgVisibilityWhenCCCvvHasInValidShortData() {
        bookHotelPage
                .enterCreditCardCvvNum(TestData.get("TestData.BillingData.InValidShort.CvvNum"))
                .clickBookNow();

        Assert.assertTrue(bookHotelPage.isCreditCardCvvFieldErrorMsgVisible(),
                "CVV error message should be visible for invalid short CVV data.");
    }

    @Test(groups = {"sanity", "negative-path"},
            description = "Should Verify CC Cvv Can Not Be Empty",
            threadPoolSize = threadPoolSize)
    public void verifyCCCvvCantBeEmpty() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardCvvFieldErrorMsgVisible(),
                "CVV error message should be visible when CVV is empty.");
        softAssert.assertEquals(bookHotelPage.getCreditCardCvvFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.EmptyCvvNum"),
                "CVV error message text mismatch.");
        softAssert.assertAll();
    }
}