package com.swaglabs.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage {

    // LOCATORS
    @FindBy(css = "[data-test='inventory-item-name']")
    private WebElement productName;

    @FindBy(css = "[data-test='inventory-item-price']")
    private WebElement productPrice;

    @FindBy(css = "[data-test='back-to-products']")
    private WebElement backButton;

    // CONSTRUCTOR
    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    // GETTERS
    public String getProductName() {
        return getText(productName);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    // ACTION
    @Step("Go back to products")
    public void clickBackToProducts() {
        click(backButton);
    }
}