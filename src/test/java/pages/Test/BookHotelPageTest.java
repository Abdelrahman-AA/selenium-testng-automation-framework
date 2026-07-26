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

    @Test
    public void verifyReturnToSearchPageWhenClickCancel() {
        bookHotelPage
                .clickCancel();
        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), selectHotelPageURL));
    }

    @Test
    public void verifyHotelNameAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getHotelNameFixedFieldText(), testedData.get(1));
    }

    @Test
    public void verifyLocationAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getLocationFixedFieldText(), testedData.get(0));
    }

    @Test
    public void verifyRoomTypeAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getRoomTypeFixedFieldText(), testedData.get(2));
    }

    @Test
    public void verifyNumOfRoomsAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getNumOfRoomsFixedFieldText().split(" ")[0].trim(), testedData.get(3).split(" ")[0].trim());
    }

    @Test
    public void verifyTotalDaysAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getTotalDaysFixedFieldText().split(" ")[0].trim(), getDifferenceBetweenTwoDatesByDays(testedArrivalDate, testedDepartureDate, Locale.UK).toString());
    }

    @Test
    public void verifyPricePerNightAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getPricePerNightFixedFieldText().split(" ")[2].trim(), testedData.get(6).split(" ")[2].trim());
    }

    @Test
    public void verifyTotalPriceBeforeGstAgainstSelected() {
        Assert.assertEquals(bookHotelPage.getTotalPriceFixedFieldText().split(" ")[2].trim(), testedData.get(7).split(" ")[2].trim());
    }

    @Test
    public void verifyGstAsTenPercent() {
        Assert.assertEquals(Integer.valueOf(bookHotelPage.getGstFixedFieldText().split(" ")[2].trim()),
                Integer.valueOf(bookHotelPage.getTotalPriceFixedFieldText().split(" ")[2].trim()) / 10);
    }

    @Test
    public void verifyFinalBillingPriceIsSummationGstAndTotalPrice() {
        Assert.assertEquals(Integer.valueOf(bookHotelPage.getFinalBilledPriceFixedFieldText().split(" ")[2].trim()),
                Integer.valueOf(bookHotelPage.getTotalPriceFixedFieldText().split(" ")[2].trim())
                        + Integer.valueOf(bookHotelPage.getGstFixedFieldText().split(" ")[2].trim()));
    }

    @Test
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
        Assert.assertTrue(isCurrentUrlEqualTo(getDriver(), bookConfirmPageURL));
    }

    //-------------------------------------
    @Test
    public void verifyFirstNameCantContainNum() {
        bookHotelPage
                .enterFirstName(TestData.get("TestData.BillingData.InValidFormat.FirstName"))
                .clickBookNow();

        Assert.assertTrue(bookHotelPage.isFirstNameFieldErrorMsgVisible());
    }

    @Test
    public void verifyFirstNameCantBeEmpty() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isFirstNameFieldErrorMsgVisible());
        softAssert.assertEquals(bookHotelPage.getFirstNameFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.EmptyFirstName"));
    }

    @Test
    public void verifyLastNameCantContainNum() {
        bookHotelPage
                .enterLastName(TestData.get("TestData.BillingData.InValidFormat.LastName"))
                .clickBookNow();

        Assert.assertTrue(bookHotelPage.isLastNameFieldErrorMsgVisible());
    }

    @Test
    public void verifyLastNameCantBeEmpty() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isLastNameFieldErrorMsgVisible());
        softAssert.assertEquals(bookHotelPage.getLastNameFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.EmptyLastName"));
        softAssert.assertAll();
    }

    @Test
    public void verifyCCNumCantContainCharacters() {
        bookHotelPage
                .enterCreditCardNum(TestData.get("TestData.BillingData.InValidFormat.CreditCardNum"))
                .clickBookNow();

        Assert.assertTrue(bookHotelPage.isLastNameFieldErrorMsgVisible());
    }

    @Test
    public void verifyCCNumCantBeLessThanSixteenDigits() {
        bookHotelPage
                .enterCreditCardNum(TestData.get("TestData.BillingData.InValidShort.CreditCardNum"))
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardNumFieldErrorMsgVisible());
        softAssert.assertEquals(bookHotelPage.getCreditCardNumFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.ErrorCreditCard"));
        softAssert.assertAll();
    }

    @Test
    public void verifyCCNumCantBeEmpty() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardNumFieldErrorMsgVisible());
        softAssert.assertEquals(bookHotelPage.getCreditCardNumFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.ErrorCreditCard"));
        softAssert.assertAll();
    }

    @Test
    public void verifyErrorMsgForNotSelectedCCType() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardTypeSelectorErrorMsgVisible());
        softAssert.assertEquals(bookHotelPage.getCreditCardTypeSelectorErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.CreditCardTypeNotSelected"));
        softAssert.assertAll();
    }

    @Test
    public void verifyErrorMsgForNotSelectedExpiryMonth() {
        bookHotelPage
                .selectCreditCardExpiryDateYearByIndex(bookHotelPage.getLastOptionOfCreditCardExpiryYearSelector())
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardExpiryDateSelectorsErrorMsgVisible());
        softAssert.assertEquals(bookHotelPage.getCreditCardExpiryDateSelectorsErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.CreditCardExpiryDateNotSelectedMonth"));
        softAssert.assertAll();
    }

    @Test
    public void verifyErrorMsgForNotSelectedExpiryYear() {
        bookHotelPage
                .selectCreditCardExpiryDateMonthByIndex(bookHotelPage.getLastOptionOfCreditCardExpiryMonthSelector())
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardExpiryDateSelectorsErrorMsgVisible());
        softAssert.assertEquals(bookHotelPage.getCreditCardExpiryDateSelectorsErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.CreditCardExpiryDateNotSelectedYear"));
        softAssert.assertAll();
    }

    @Test
    public void verifyErrorMsgForNotSelectedExpiryMonthAndYear() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardExpiryDateSelectorsErrorMsgVisible());
        softAssert.assertEquals(bookHotelPage.getCreditCardExpiryDateSelectorsErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.CreditCardExpiryDateNotSelectedMonth"));
        softAssert.assertAll();
    }

    @Test
    public void verifyErrorMsgVisibilityWhenCCCvvHasInValidShortData() {
        bookHotelPage
                .enterCreditCardCvvNum(TestData.get("TestData.BillingData.InValidShort.CvvNum"))
                .clickBookNow();

        Assert.assertTrue(bookHotelPage.isCreditCardCvvFieldErrorMsgVisible());
    }

    @Test
    public void verifyCCCvvCantBeEmpty() {
        bookHotelPage
                .clickBookNow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookHotelPage.isCreditCardCvvFieldErrorMsgVisible());
        softAssert.assertEquals(bookHotelPage.getCreditCardCvvFieldErrorMsg(), TestData.get("Pages.BookHotelPage.Errors.EmptyCvvNum"));
        softAssert.assertAll();
    }
}
