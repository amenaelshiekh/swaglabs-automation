package com.swaglabs.tests.e2e;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.*;
import com.swaglabs.utils.ConfigReader;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("End-to-End")
@Feature("Purchase journey")
public class CheapestItemE2ETest extends BaseTest {

    @Test
    @Story("A user can sort by price, buy the cheapest item, and check out")
    @Severity(SeverityLevel.NORMAL)
    public void buyCheapestItemJourney() {
        // 1. Log in
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));

        // 2. Sort by price low -> high, capture the cheapest price
        InventoryPage inventory = new InventoryPage(driver);
        inventory.sortBy("Price (low to high)");

        List<Double> prices = inventory.getItemPrices();
        double cheapest = prices.get(0);
        assertThat(prices)
                .as("Prices should be ascending after sorting low to high")
                .isSorted();

        // 3. Add the first (cheapest) item and go to cart
        inventory.addFirstItemToCart();
        inventory.goToCart();

        CartPage cart = new CartPage(driver);
        assertThat(cart.getItemCount())
                .as("Cart should hold exactly the one cheapest item")
                .isEqualTo(1);

        // 4. Check out fully
        cart.clickCheckout();
        new CheckoutStepOnePage(driver).fillForm("John", "Doe", "12345");

        // 5. Verify the overview total matches the cheapest price we captured
        CheckoutStepTwoPage overview = new CheckoutStepTwoPage(driver);
        assertThat(overview.getSubtotal())
                .as("Overview total should equal the cheapest item's price")
                .isEqualTo(cheapest);

        // 6. Finish and confirm
        overview.clickFinish();
        assertThat(new CheckoutCompletePage(driver).getConfirmationMessage())
                .as("Order confirmation should be shown")
                .contains("Thank you for your order");
    }
}