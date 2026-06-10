package com.swaglabs.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

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

    @FindBy(css = "[data-test='add-to-cart-sauce-labs-bike-light']")
    private WebElement addBikeLightButton;

    @FindBy(css = "[data-test='shopping-cart-link']")
    private WebElement cartLink;

    @FindBy(css = "[data-test='product-sort-container']")
    private WebElement sortDropdown;

    @FindBy(className = "inventory_item_price")
    private java.util.List<WebElement> itemPrices;

    @FindBy(className = "inventory_item_name")
    private java.util.List<WebElement> itemNames;

    @FindBy(css = "[data-test='item-4-title-link']")
    private WebElement backpackNameLink;

    @FindBy(css = "button[data-test^='add-to-cart']")
    private java.util.List<WebElement> addToCartButtons;

    // CONSTRUCTOR
    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    // ACTIONS
    @Step("Add backpack to cart")
    public void addBackpackToCart() {
        click(addBackpackButton);
    }

    @Step("Remove backpack from cart")
    public void removeBackpackFromCart() {
        click(removeBackpackButton);
    }

    @Step("Add bike light to cart")
    public void addBikeLightToCart() {
        click(addBikeLightButton);
    }

    @Step("Open the cart")
    public void goToCart() {
        click(cartLink);
    }

    public void openBackpackDetail() {
        click(backpackNameLink);
    }

    @Step("Sort products by '{visibleText}'")
    public void sortBy(String visibleText) {
        new Select(sortDropdown).selectByVisibleText(visibleText);
    }

    @Step("Add first item to cart")
    public void addFirstItemToCart() {
        click(addToCartButtons.get(0));
    }

    // GETTERS
    public int getProductCount() {
        return inventoryItems.size();
    }

    public int getCartItemCount() {
        java.util.List<WebElement> badge =
                driver.findElements(By.cssSelector("[data-test='shopping-cart-badge']"));
        return badge.isEmpty() ? 0 : Integer.parseInt(badge.get(0).getText());
    }

    public boolean isCartBadgeVisible() {
        return !driver.findElements(
                By.cssSelector("[data-test='shopping-cart-badge']")).isEmpty();
    }

    public boolean isAddBackpackButtonVisible() {
        try {
            return isDisplayed(addBackpackButton);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public java.util.List<String> getItemNames() {
        return itemNames.stream().map(this::getText)
                .collect(java.util.stream.Collectors.toList());
    }

    public java.util.List<Double> getItemPrices() {
        return itemPrices.stream()
                .map(e -> Double.parseDouble(getText(e).replace("$", "")))
                .collect(java.util.stream.Collectors.toList());
    }
}