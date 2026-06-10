package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.InventoryPage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.pages.MenuPage;
import com.swaglabs.utils.ConfigReader;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authentication")
@Feature("Session security")
public class SessionTest extends BaseTest {

    @Test
    @Story("Inventory cannot be reached directly without logging in")
    @Severity(SeverityLevel.CRITICAL)
    public void directUrlWhileLoggedOutIsBlocked() {
        driver.get(ConfigReader.get("base.url") + "inventory.html");

        assertThat(new LoginPage(driver).getErrorMessage())
                .as("Direct access to a protected page should be blocked")
                .contains("You can only access '/inventory.html' when you are logged in");
    }

    @Test
    @Story("Back button after logout does not restore the session")
    @Severity(SeverityLevel.CRITICAL)
    public void backButtonAfterLogoutIsBlocked() {
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));
        new MenuPage(driver).logout();
        driver.navigate().back();

        assertThat(new LoginPage(driver).getErrorMessage())
                .as("Browser back after logout should not re-enter the app")
                .contains("You can only access '/inventory.html' when you are logged in");
    }

    @Test
    @Story("Cart contents persist across logout and re-login")
    @Severity(SeverityLevel.NORMAL)
    public void cartPersistsAfterReLogin() {
        // add an item
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        assertThat(inventoryPage.getCartItemCount())
                .as("Backpack should be in the cart before logout")
                .isEqualTo(1);

        // logout, then log back in as the same user
        new MenuPage(driver).logout();
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));

        // cart should still hold the item
        InventoryPage afterReLogin = new InventoryPage(driver);
        assertThat(afterReLogin.getCartItemCount())
                .as("Cart should still contain the item after logging back in")
                .isEqualTo(1);
    }
}