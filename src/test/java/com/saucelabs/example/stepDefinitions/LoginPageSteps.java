package com.saucelabs.example.stepDefinitions;

import com.saucelabs.example.SauceThrottle;
import com.saucelabs.example.Util;
import com.saucelabs.example.pages.InventoryPage;
import com.saucelabs.example.pages.LoginPage;
import com.saucelabs.example.pages.PagesFactory;
import cucumber.api.java8.En;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.util.Map;

public class LoginPageSteps implements En
{
    public LoginPageSteps()
    {
        Given("^The user is on the Home Page$", () -> {
            PagesFactory pf = PagesFactory.getInstance();
            RemoteWebDriver driver = pf.getDriver();
            Util.info(driver, ">>> The user is on the Home Page");

            LoginPage loginPage = pf.getLoginPage();
            loginPage.navigateTo(LoginPage.PAGE_URL);
        });

        And("^The user provides the username as \"([^\"]*)\" and password as \"([^\"]*)\"$", (String username, String password) -> {
            PagesFactory pf = PagesFactory.getInstance();
            RemoteWebDriver driver = pf.getDriver();
            Util.info(driver, ">>> The user provides the username as \"username\" and password as \"password\"");

            LoginPage loginPage = pf.getLoginPage();
            loginPage.enterUsername(username);
            loginPage.enterPassword(password);
            Util.sleep(2000);
        });

        And("^The user clicks the 'Login' button$", () -> {
            PagesFactory pf = PagesFactory.getInstance();
            RemoteWebDriver driver = pf.getDriver();
            Util.info(driver, ">>> The user clicks the 'Login' button");

            LoginPage loginPage = pf.getLoginPage();
            loginPage.clickLogin();
//            Util.sleep(3000);
        });

        Then("^The user should login successfully and is brought to the inventory page$", () -> {
            PagesFactory pf = PagesFactory.getInstance();
            RemoteWebDriver driver = pf.getDriver();
            Util.info(driver, ">>> The user should login successfully and is brought to the inventory page");

            InventoryPage inventoryPage = pf.getInventoryPage();
            inventoryPage.waitForPageLoad();

            String currentUrl = PagesFactory.getInstance().getDriver().getCurrentUrl();
            Assert.assertEquals(currentUrl, InventoryPage.PAGE_URL);

//            Util.takeScreenShot(driver);
        });


        Then("^The user should be shown an invalid username/password message$", () -> {
            PagesFactory pf = PagesFactory.getInstance();
            RemoteWebDriver driver = pf.getDriver();
            Util.info(driver, ">>> The user should be shown an invalid username/password message");

            LoginPage loginPage = pf.getLoginPage();
            Assert.assertTrue(loginPage.hasUsernamePasswordError());
        });
    }
}
