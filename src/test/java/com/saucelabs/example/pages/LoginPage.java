package com.saucelabs.example.pages;

import com.saucelabs.example.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPage
{
    public static final String PAGE_URL = "https://www.saucedemo.com";

    @FindBy(xpath = "//input[@data-test='username']")
    private WebElement usernameElem;

    @FindBy(xpath = "//input[@data-test='password']")
    private WebElement passwordElem;

    @FindBy(xpath = "//input[@value='LOGIN']")
    private WebElement loginElem;

    public LoginPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public WebElement getPageLoadedTestElement()
    {
        return loginElem;
    }

    public void login()
    {
        Util.info(getDriver(), "Logging in...");

        try
        {
            loginElem.click();
        }
        catch (org.openqa.selenium.TimeoutException e)
        {
            Util.info(getDriver(), "Timeout clicking login: " + e.getClass().getSimpleName());

        }
        catch (Exception e)
        {
            Util.info(getDriver(), "Clicking login, caught exception, type=" + e.getClass().getSimpleName());
        }
    }

    public void clickLogin()
    {
        loginElem.click();
    }

    public void enterPassword(String password)
    {
        passwordElem.click();
        passwordElem.sendKeys(password);
    }

    public void enterUsername(String username)
    {
        usernameElem.click();
        usernameElem.sendKeys(username);
    }

    public boolean hasLockedOutError()
    {
        WebElement elem = getDriver().findElement(By.xpath("//button[@class='error-button']"));
        return elem.isDisplayed();
    }

    public boolean hasUsernamePasswordError()
    {
        WebElement elem = getDriver().findElement(By.xpath("//button[@class='error-button']"));
        return elem.isDisplayed();
    }
}
