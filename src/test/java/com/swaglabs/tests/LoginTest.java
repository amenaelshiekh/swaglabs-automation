package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.ConfigReader;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {

    @Test
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("standard.username"), ConfigReader.get("password"));

        assertThat(driver.getCurrentUrl())
                .as("Should land on the inventory page after a valid login")
                .contains("inventory");
    }

    @Test
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("locked.username"), ConfigReader.get("password"));

        assertThat(loginPage.getErrorMessage())
                .as("Locked-out user should see a 'locked out' error")
                .contains("locked out");
    }
}