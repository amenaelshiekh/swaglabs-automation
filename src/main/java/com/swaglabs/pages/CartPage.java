package com.swaglabs.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    // LOCATORS
    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(css = "[data-test='remove-sauce-labs-backpack']")
    private WebElement removeBackpackButton;

    @FindBy(css = "[data-test='checkout']")
    private WebElement checkoutButton;

    // CONSTRUCTOR
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ACTIONS
    @Step("Remove backpack from cart")
    public void removeBackpack() {
        click(removeBackpackButton);
    }

    @Step("Proceed to checkout")
    public void clickCheckout() {
        click(checkoutButton);
    }

    // GETTERS
    public int getItemCount() {
        return itemNames.size();
    }

    public List<String> getItemNames() {
        return itemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean cartContains(String productName) {
        return getItemNames().contains(productName);
    }
}