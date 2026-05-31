package com.swaglabs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutStepTwoPage extends BasePage {

    // LOCATORS
    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(css = "[data-test='subtotal-label']")   // "Item total: $29.99"
    private WebElement subtotalLabel;

    @FindBy(css = "[data-test='tax-label']")         // "Tax: $2.40"
    private WebElement taxLabel;

    @FindBy(css = "[data-test='total-label']")       // "Total: $32.39"
    private WebElement totalLabel;

    @FindBy(css = "[data-test='finish']")
    private WebElement finishButton;

    // CONSTRUCTOR
    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    // ACTIONS
    public void clickFinish() {
        click(finishButton);
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

    // Pulls the number out of a label like "Item total: $29.99" -> 29.99
    public double getSubtotal() {
        return parseAmount(getText(subtotalLabel));
    }

    public double getTax() {
        return parseAmount(getText(taxLabel));
    }

    public double getTotal() {
        return parseAmount(getText(totalLabel));
    }

    private double parseAmount(String labelText) {
        // labelText e.g. "Item total: $29.99" -> grab everything after the "$"
        String number = labelText.substring(labelText.indexOf("$") + 1);
        return Double.parseDouble(number.trim());
    }
}