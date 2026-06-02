package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.*;
import com.swaglabs.utils.ConfigReader;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Menu")
public class MenuTest extends BaseTest {

    private InventoryPage inventoryPage;
    private MenuPage menuPage;

    @BeforeMethod
    public void loginFirst(){
        new LoginPage(driver).login(
                ConfigReader.get("standard.username"),
                ConfigReader.get("password"));

                inventoryPage = new InventoryPage(driver);
                menuPage = new MenuPage(driver);
    }

    @Test
    @Story("User can log out")
    @Severity(SeverityLevel.NORMAL)
    public void logoutReturnsToLoginPage(){
        menuPage.logout();

        assertThat(driver.getCurrentUrl())
                .as("Logout should return to the login page")
                .endsWith("saucedemo.com/");
    }

    @Test
    @Story("Reset App State clears the cart")
    @Severity(SeverityLevel.MINOR)
    public void resetAppStateClearsCart() {
        inventoryPage.addBackpackToCart();
        assertThat(inventoryPage.getCartItemCount())
                .as("Badge should show 1 before reset")
                .isEqualTo(1);

        menuPage.resetAppState();

        // badge cleared
        assertThat(inventoryPage.isCartBadgeVisible())
                .as("Cart badge should be gone after Reset App State")
                .isFalse();

        // stronger proof: cart page actually lists 0 items
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        assertThat(cartPage.getItemCount())
                .as("Cart page should list 0 items after reset")
                .isEqualTo(0);
    }

    @Test
    @Story("KNOWN BUG (BUG-001): Reset App State should restore Add-to-cart buttons")
    @Issue("BUG-001")
    @Severity(SeverityLevel.MINOR)
    public void resetShouldRestoreAddButton() {
        inventoryPage.addBackpackToCart();
        menuPage.resetAppState();

        assertThat(inventoryPage.isAddBackpackButtonVisible())
                .as("BUG-001: after Reset App State the Add-to-cart button should be restored")
                .isTrue();
    }
}
