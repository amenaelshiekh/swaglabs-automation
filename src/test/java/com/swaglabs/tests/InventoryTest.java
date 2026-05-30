package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.InventoryPage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.ConfigReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void shouldShowSixProducts() {
        assertThat(inventoryPage.getProductCount())
                .as("Inventory should list 6 products")
                .isEqualTo(6);
    }

    @Test
    public void addingOneItemShowsBadgeCountOne() {   // P2
        inventoryPage.addBackpackToCart();
        assertThat(inventoryPage.getCartItemCount())
                .as("Cart badge should show 1 after adding one item")
                .isEqualTo(1);
    }

    @Test
    public void removingItemClearsBadge() {           // B1 bonus
        inventoryPage.addBackpackToCart();
        inventoryPage.removeBackpackFromCart();
        assertThat(inventoryPage.isCartBadgeVisible())
                .as("Cart badge should disappear after removing the only item")
                .isFalse();
    }
}