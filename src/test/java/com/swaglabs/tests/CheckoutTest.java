package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.*;
import com.swaglabs.utils.ConfigReader;
import io.qameta.allure.*;
import org.testng.annotations.*;


import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {

    private CheckoutStepOnePage checkoutPage;

    @BeforeMethod
    public void reachCheckoutForm() {
        // log in -> add an item -> open cart -> click checkout
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();

        new CartPage(driver).clickCheckout();

        checkoutPage = new CheckoutStepOnePage(driver);
    }

    @Test
    @Story("Completing checkout shows the order confirmation")
    @Severity(SeverityLevel.CRITICAL)
    public void completingCheckoutShowsConfirmation() {
        // @BeforeMethod already logged in, added a backpack, and reached step one.
        checkoutPage.fillForm("John", "Doe", "12345");      // step one -> step two

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(driver);
        assertThat(stepTwo.getItemCount())
                .as("Overview should list the 1 item being purchased")
                .isEqualTo(1);

        stepTwo.clickFinish();                              // step two -> complete

        CheckoutCompletePage complete = new CheckoutCompletePage(driver);
        assertThat(complete.getConfirmationMessage())
                .as("Should show the order confirmation message")
                .contains("Thank you for your order");
    }

    @Test
    @Story("Checkout requires a first name")
    @Severity(SeverityLevel.NORMAL)
    public void missingFirstNameShowsError() {
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();

        assertThat(checkoutPage.getErrorMessage())
                .as("Should warn that first name is required")
                .contains("First Name is required");
    }

    @Test
    @Story("Checkout requires a last name")
    @Severity(SeverityLevel.MINOR)
    public void missingLastNameShowsError() {
        checkoutPage.enterFirstName("John");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();

        assertThat(checkoutPage.getErrorMessage())
                .as("Should warn that last name is required")
                .contains("Last Name is required");
    }

    @Test
    @Story("Checkout requires a postal code")
    @Severity(SeverityLevel.NORMAL)
    public void missingPostalCodeShowsError() {
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.clickContinue();

        assertThat(checkoutPage.getErrorMessage())
                .as("Should warn that postal code is required")
                .contains("Postal Code is required");
    }

}