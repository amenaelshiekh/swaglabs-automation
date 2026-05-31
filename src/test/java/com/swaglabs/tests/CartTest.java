package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.CartPage;
import com.swaglabs.pages.InventoryPage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.ConfigReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Cart")
public class CartTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginFirst() {
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    @Story("Adding multiple items reflects in the cart")
    @Severity(SeverityLevel.CRITICAL)
    public void addingTwoItemsShowsBothInCart() {          // P3
        inventoryPage.addBackpackToCart();
        inventoryPage.addBikeLightToCart();

        assertThat(inventoryPage.getCartItemCount())
                .as("Cart badge should show 2 after adding two items")
                .isEqualTo(2);

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);

        assertThat(cartPage.getItemCount())
                .as("Cart should list 2 items")
                .isEqualTo(2);
        assertThat(cartPage.cartContains("Sauce Labs Backpack"))
                .as("Cart should contain the backpack")
                .isTrue();
        assertThat(cartPage.cartContains("Sauce Labs Bike Light"))
                .as("Cart should contain the bike light")
                .isTrue();
    }

    @Test
    @Story("Removing an item from the cart")
    @Severity(SeverityLevel.NORMAL)
    public void removingItemFromCartUpdatesList() {        // B1 (cart-screen version)
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        assertThat(cartPage.getItemCount()).isEqualTo(1);

        cartPage.removeBackpack();

        assertThat(cartPage.getItemCount())
                .as("Cart should be empty after removing the only item")
                .isEqualTo(0);
    }
}