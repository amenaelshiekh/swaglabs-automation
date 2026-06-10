package com.swaglabs.tests.e2e;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.*;
import com.swaglabs.utils.ConfigReader;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("End-to-End")
@Feature("Purchase journey")
public class PurchaseJourneyE2ETest extends BaseTest {

    @Test
    @Story("A user can log in, add items, check out, and see confirmation")
    @Severity(SeverityLevel.CRITICAL)
    public void completePurchaseJourney() {
        // 1. Log in
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));

        // 2. Add two products and verify the badge
        InventoryPage inventory = new InventoryPage(driver);
        inventory.addBackpackToCart();
        inventory.addBikeLightToCart();
        assertThat(inventory.getCartItemCount())
                .as("Cart badge should show 2 after adding two items")
                .isEqualTo(2);

        // 3. Open the cart and confirm both items are listed
        inventory.goToCart();
        CartPage cart = new CartPage(driver);
        assertThat(cart.getItemCount())
                .as("Cart should list both items")
                .isEqualTo(2);

        // 4. Check out: fill the form and continue
        cart.clickCheckout();
        new CheckoutStepOnePage(driver).fillForm("John", "Doe", "12345");

        // 5. On the overview, verify the item total equals the sum of the two prices
        CheckoutStepTwoPage overview = new CheckoutStepTwoPage(driver);
        assertThat(overview.getItemCount())
                .as("Overview should list both items")
                .isEqualTo(2);
        // backpack $29.99 + bike light $9.99 = $39.98
        assertThat(overview.getSubtotal())
                .as("Item total should equal the sum of the two item prices")
                .isEqualTo(39.98);

        // 6. Finish and verify the confirmation
        overview.clickFinish();
        assertThat(new CheckoutCompletePage(driver).getConfirmationMessage())
                .as("Order confirmation should be shown")
                .contains("Thank you for your order");

        // 7. Log out and verify we're back on the login page
        new MenuPage(driver).logout();
        assertThat(driver.getCurrentUrl())
                .as("Should be back on the login page after logout")
                .endsWith("saucedemo.com/");
    }
}