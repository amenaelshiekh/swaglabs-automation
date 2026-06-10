package com.swaglabs.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    // 1. LOCATORS
    @FindBy(id = "user-name")            private WebElement usernameField;
    @FindBy(id = "password")             private WebElement passwordField;
    @FindBy(id = "login-button")         private WebElement loginButton;
    @FindBy(css = "[data-test='error']") private WebElement errorMessage;

    // 2. CONSTRUCTOR - pass the driver up to BasePage
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // 3. ACTIONS
    @Step("Log in as '{username}'")
    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    // 4. GETTERS - what a test can READ from this page
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}