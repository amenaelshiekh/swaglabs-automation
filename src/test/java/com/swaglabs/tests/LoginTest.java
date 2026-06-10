package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.ConfigReader;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    @Test
    @Story("Valid credentials log the user in")
    @Severity(SeverityLevel.CRITICAL)
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("standard.username"), ConfigReader.get("password"));

        assertThat(driver.getCurrentUrl())
                .as("Should land on the inventory page after a valid login")
                .contains("inventory");
    }

    @Test
    @Story("Locked-out user is rejected")
    @Severity(SeverityLevel.NORMAL)
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("locked.username"), ConfigReader.get("password"));

        assertThat(loginPage.getErrorMessage())
                .as("Locked-out user should see a 'locked out' error")
                .contains("locked out");
    }

    @Test
    @Story("Wrong password is rejected")
    @Severity(SeverityLevel.NORMAL)
    public void testWrongPassword() {                       // N2
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("standard.username"), "wrong_password");

        assertThat(loginPage.getErrorMessage())
                .as("Wrong password should be rejected")
                .contains("Username and password do not match");
    }

    @Test
    @Story("Empty username is rejected")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyUsername() {                       // N3
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", ConfigReader.get("password"));

        assertThat(loginPage.getErrorMessage())
                .as("Empty username should be rejected")
                .contains("Username is required");
    }

    @Test
    @Story("Empty password is rejected")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyPassword() {                       // N4
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("standard.username"), "");

        assertThat(loginPage.getErrorMessage())
                .as("Empty password should be rejected")
                .contains("Password is required");
    }

    @Test
    @Story("Credentials with surrounding whitespace are rejected")
    @Severity(SeverityLevel.MINOR)
    public void whitespaceInCredentialsIsRejected() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(" standard_user ", "secret_sauce");

        assertThat(loginPage.getErrorMessage())
                .as("Untrimmed credentials should not log the user in")
                .contains("Username and password do not match");
    }

    @Test
    @Story("Usernames are case-sensitive")
    @Severity(SeverityLevel.MINOR)
    public void usernameCaseSensitivityIsRejected() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Standard_User", ConfigReader.get("password"));

        assertThat(loginPage.getErrorMessage())
                .as("Mixed-case username should not match the lowercase account")
                .contains("Username and password do not match");
    }
}