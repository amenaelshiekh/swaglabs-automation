package com.swaglabs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutStepOnePage extends BasePage {

    // LOCATORS
    @FindBy(css = "[data-test='firstName']")  private WebElement firstNameField;
    @FindBy(css = "[data-test='lastName']")   private WebElement lastNameField;
    @FindBy(css = "[data-test='postalCode']") private WebElement postalCodeField;
    @FindBy(css = "[data-test='continue']")   private WebElement continueButton;
    @FindBy(css = "[data-test='error']")      private WebElement errorMessage;

    // CONSTRUCTOR
    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    // ACTIONS
    public void enterFirstName(String value) { type(firstNameField, value); }
    public void enterLastName(String value)  { type(lastNameField, value); }
    public void enterPostalCode(String value){ type(postalCodeField, value); }
    public void clickContinue()              { click(continueButton); }

    // a convenience action: fill everything then continue (used by positive tests later)
    public void fillForm(String first, String last, String postal) {
        enterFirstName(first);
        enterLastName(last);
        enterPostalCode(postal);
        clickContinue();
    }

    // GETTER
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}