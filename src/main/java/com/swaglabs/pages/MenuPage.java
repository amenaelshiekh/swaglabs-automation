package com.swaglabs.pages;

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

    public void logout() {
        click(menuButton);
        click(logoutLink);     
    }

    public void resetAppState() {
        click(menuButton);
        click(resetLink);
    }
}