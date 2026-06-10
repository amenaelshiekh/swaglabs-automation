package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.CartPage;
import com.swaglabs.pages.InventoryPage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.ConfigReader;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Checkout")
public class EmptyCheckoutTest extends BaseTest {

    @BeforeMethod
    public void loginOnly() {
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));
    }

    @Test
    @Story("KNOWN BUG (BUG-003): checkout should be blocked when the cart is empty")
    @Issue("BUG-003")
    @Severity(SeverityLevel.NORMAL)
    public void checkoutShouldBeBlockedWithEmptyCart() {   // intentionally FAILS - bugs.md BUG-003
        InventoryPage inventoryPage = new InventoryPage(driver);
        // cart is empty - go straight to it and try to check out
        inventoryPage.goToCart();
        new CartPage(driver).clickCheckout();

        // EXPECTED: still on the cart page (checkout blocked).
        // ACTUAL: advances to checkout-step-one with an empty cart (BUG-003).
        assertThat(driver.getCurrentUrl())
                .as("BUG-003: checkout with an empty cart should not proceed")
                .contains("cart.html");
    }
}