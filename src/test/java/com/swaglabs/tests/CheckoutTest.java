package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.*;
import com.swaglabs.utils.CheckoutData;
import com.swaglabs.utils.ConfigReader;
import com.swaglabs.utils.JsonReader;
import com.swaglabs.utils.LoginData;
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

    @DataProvider(name = "checkoutNegatives")
    public Object[][] checkoutNegatives() {
        CheckoutData[] data = JsonReader.readArray("testdata/checkout-data.json", CheckoutData[].class);
        Object[][] rows = new Object[data.length][1];
        for (int i = 0; i < data.length; i++) {
            rows[i][0] = data[i];
        }
        return rows;
    }

    @Test(dataProvider = "checkoutNegatives")
    @Story("Checkout form fields should be required")
    @Severity(SeverityLevel.NORMAL)
    public void checkoutShouldRequireAllFields(CheckoutData data) {
        checkoutPage.enterFirstName(data.firstName);
        checkoutPage.enterLastName(data.lastName);
        checkoutPage.enterPostalCode(data.postalCode);
        checkoutPage.clickContinue();

        assertThat(checkoutPage.getErrorMessage())
                .as("Scenario '%s' should show the expected error", data.scenario)
                .contains(data.expectedError);

    }

    @Test
    @Story("KNOWN BUG (BUG-002): checkout should validate field content, not just non-emptiness")
    @Issue("BUG-002")
    @Severity(SeverityLevel.MINOR)
    public void checkoutShouldRejectInvalidFieldContent() {   // intentionally FAILS - bugs.md BUG-002
        // Invalid content: whitespace-only name + non-numeric postal code.
        // A validating form should reject these and keep us on step one.
        checkoutPage.fillForm("   ", "Doe", "abcde");

        // EXPECTED: still on checkout-step-one (rejected). ACTUAL: advances to step-two (BUG-002).
        assertThat(driver.getCurrentUrl())
                .as("BUG-002: invalid field content should keep the user on step one")
                .contains("checkout-step-one");
    }

    @Test
    @Story("Cart contents survive cancelling checkout")
    @Severity(SeverityLevel.NORMAL)
    public void cartPersistsAfterCancellingCheckout() {
        // @BeforeMethod added a backpack and reached checkout step one.
        checkoutPage.clickCancel();          // back to inventory (or cart, depending on step)

        // cart should still hold the backpack
        InventoryPage inventoryPage = new InventoryPage(driver);
        assertThat(inventoryPage.getCartItemCount())
                .as("Cart should still contain the item after cancelling checkout")
                .isEqualTo(1);
    }

}