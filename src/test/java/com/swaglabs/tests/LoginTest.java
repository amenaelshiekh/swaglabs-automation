package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.ConfigReader;
import org.testng.annotations.Test;
import io.qameta.allure.*;
import com.swaglabs.utils.JsonReader;
import com.swaglabs.utils.LoginData;
import org.testng.annotations.DataProvider;

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

    @DataProvider(name = "loginNegatives")
    public Object[][] loginNegatives() {
        LoginData[] data = JsonReader.readArray("testdata/login-data.json", LoginData[].class);
        Object[][] rows = new Object[data.length][1];
        for (int i = 0; i < data.length; i++) {
            rows[i][0] = data[i];
        }
        return rows;
    }

    @Test(dataProvider = "loginNegatives")
    @Story("Invalid login attempts are rejected")
    @Severity(SeverityLevel.NORMAL)
    public void invalidLoginShowsError(LoginData data) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(data.username, data.password);

        assertThat(loginPage.getErrorMessage())
                .as("Scenario '%s' should be rejected", data.scenario)
                .contains(data.expectedError);
    }
}