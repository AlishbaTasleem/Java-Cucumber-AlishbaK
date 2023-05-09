package com.saucelabs.example.pages;

import com.saucelabs.example.MyFluentWait;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.time.temporal.ChronoUnit;

abstract class AbstractPage
{
    private WebDriver driver;
    protected Wait<WebDriver> wait;

    AbstractPage(WebDriver driver)
    {
        this.driver = driver;

        wait = new MyFluentWait<WebDriver>(driver)
                .withTimeout(60, ChronoUnit.SECONDS)
                .pollingEvery(2, ChronoUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    public abstract WebElement getPageLoadedTestElement();

    protected WebDriver getDriver()
    {
        return driver;
    }

    protected Wait<WebDriver> getWait()
    {
        return wait;
    }

    protected void setWait(Wait<WebDriver> wait)
    {
        this.wait = wait;
    }

    public void waitForPageLoad()
    {
        WebElement testElement = getPageLoadedTestElement();

        // Wait for the page to load...
//        wait.until(ExpectedConditions.titleContains(this.getTitle()));
//        wait.until(ExpectedConditions.elementToBeClickable(testElement));
        wait.until(ExpectedConditions.visibilityOf(testElement));
    }

    protected void moveTo(WebElement elem)
    {
        // Work around for Firefox lacking a moveToElement implementation in their driver...
        // @see https://github.com/mozilla/geckodriver/issues/776

        if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().equals("firefox"))
        {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        }
        else
        {
            Actions actions = new Actions(driver);
            actions.moveToElement(elem).build().perform();
        }
    }

    protected boolean isPageLoaded(WebElement elem)
    {
        boolean isLoaded = false;

        try
        {
            isLoaded = elem.isDisplayed();
        }
        catch (NoSuchElementException e)
        {
        }

        return isLoaded;
    }

    public void navigateTo(String url)
    {
        WebDriver driver = getDriver();

        try
        {
            driver.navigate().to(url);
//            driver.get(url);
        }
        catch (Exception e)
        {
            if (e instanceof TimeoutException)
            {
                System.out.println("Timeout loading home page");
            }
            else if (e instanceof ScriptTimeoutException)
            {
                System.out.println("Script Timeout loading home page");
            }
            else
            {
                System.err.println(e.getMessage());
            }
        }
    }

    public boolean sleep(long msecs)
    {
        boolean interrupted = false;

        try
        {
            Thread.sleep(msecs);
        }
        catch (InterruptedException e)
        {
            interrupted = true;
        }

        return interrupted;
    }

}
