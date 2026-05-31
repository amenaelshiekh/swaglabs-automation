package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.InventoryPage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.ConfigReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Inventory")
public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginFirst() {
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    @Story("Inventory displays all products")
    @Severity(SeverityLevel.NORMAL)
    public void shouldShowSixProducts() {
        assertThat(inventoryPage.getProductCount())
                .as("Inventory should list 6 products")
                .isEqualTo(6);
    }

    @Test
    @Story("Adding an item updates the cart badge")
    @Severity(SeverityLevel.CRITICAL)
    public void addingOneItemShowsBadgeCountOne() {
        inventoryPage.addBackpackToCart();
        assertThat(inventoryPage.getCartItemCount())
                .as("Cart badge should show 1 after adding one item")
                .isEqualTo(1);
    }

    @Test
    @Story("Removing an item clears the cart badge")
    @Severity(SeverityLevel.NORMAL)
    public void removingItemClearsBadge() {
        inventoryPage.addBackpackToCart();
        inventoryPage.removeBackpackFromCart();
        assertThat(inventoryPage.isCartBadgeVisible())
                .as("Cart badge should disappear after removing the only item")
                .isFalse();
    }
}