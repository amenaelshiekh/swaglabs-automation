package com.swaglabs.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InventoryPage extends BasePage {

    // LOCATORS
    @FindBy(css = "[data-test='add-to-cart-sauce-labs-backpack']")
    private WebElement addBackpackButton;

    @FindBy(css = "[data-test='remove-sauce-labs-backpack']")
    private WebElement removeBackpackButton;

    @FindBy(css = "[data-test='shopping-cart-badge']")
    private WebElement cartBadge;

    @FindBy(className = "inventory_item")
    private java.util.List<WebElement> inventoryItems;

    // CONSTRUCTOR
    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    // ACTIONS
    public void addBackpackToCart() {
        click(addBackpackButton);
    }

    public void removeBackpackFromCart() {
        click(removeBackpackButton);
    }

    // GETTERS
    public int getProductCount() {
        return inventoryItems.size();
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(getText(cartBadge));
        } catch (NoSuchElementException e) {
            return 0; // no badge element shown = empty cart
        }
    }

    public boolean isCartBadgeVisible() {
        try {
            return isDisplayed(cartBadge);
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}