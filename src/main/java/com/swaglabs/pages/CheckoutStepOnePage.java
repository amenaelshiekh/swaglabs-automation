package com.swaglabs.pages;

import io.qameta.allure.Step;
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
    @FindBy(css = "[data-test='cancel']")   private WebElement cancelButton;

    // CONSTRUCTOR
    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    // ACTIONS
    public void enterFirstName(String value) { type(firstNameField, value); }

    public void enterLastName(String value)  { type(lastNameField, value); }

    public void enterPostalCode(String value){ type(postalCodeField, value); }

    @Step("Continue to overview")
    public void clickContinue()              { click(continueButton); }

    @Step("Fill checkout info: {first} {last}, {postal}")
    public void fillForm(String first, String last, String postal) {
        enterFirstName(first);
        enterLastName(last);
        enterPostalCode(postal);
        clickContinue();
    }

    public void clickCancel() {
        click(cancelButton);
    }

    // GETTER
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}