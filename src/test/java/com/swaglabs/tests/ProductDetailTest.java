package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.InventoryPage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.pages.ProductDetailPage;
import com.swaglabs.utils.ConfigReader;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Product detail")
public class ProductDetailTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginFirst() {
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    @Story("Opening a product shows its detail page")
    @Severity(SeverityLevel.NORMAL)
    public void openingProductShowsItsDetail() {
        inventoryPage.openBackpackDetail();

        ProductDetailPage detail = new ProductDetailPage(driver);
        assertThat(detail.getProductName())
                .as("Detail page should show the backpack's name")
                .isEqualTo("Sauce Labs Backpack");
        assertThat(detail.getProductPrice())
                .as("Detail page should show the backpack's price")
                .isEqualTo("$29.99");
    }

    @Test
    @Story("Back to products returns to the inventory")
    @Severity(SeverityLevel.MINOR)
    public void backToProductsReturnsToInventory() {
        inventoryPage.openBackpackDetail();

        ProductDetailPage detail = new ProductDetailPage(driver);
        detail.clickBackToProducts();

        assertThat(driver.getCurrentUrl())
                .as("Back to products should return to the inventory page")
                .contains("inventory.html");
    }
}