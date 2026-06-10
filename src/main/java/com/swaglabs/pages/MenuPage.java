package com.swaglabs.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuPage extends BasePage {

    @FindBy(id = "react-burger-menu-btn")     private WebElement menuButton;
    @FindBy(id = "logout_sidebar_link")        private WebElement logoutLink;
    @FindBy(id = "reset_sidebar_link")         private WebElement resetLink;

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    @Step("Log out")
    public void logout() {
        click(menuButton);
        click(logoutLink);     
    }

    @Step("Reset app state")
    public void resetAppState() {
        click(menuButton);
        click(resetLink);
    }
}