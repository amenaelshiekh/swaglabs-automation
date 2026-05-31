package com.swaglabs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    // LOCATORS
    @FindBy(css = "[data-test='complete-header']")   // "Thank you for your order!"
    private WebElement completeHeader;

    @FindBy(css = "[data-test='back-to-products']")
    private WebElement backHomeButton;

    // CONSTRUCTOR
    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    // GETTER
    public String getConfirmationMessage() {
        return getText(completeHeader);
    }

    public void clickBackHome() {
        click(backHomeButton);
    }
}