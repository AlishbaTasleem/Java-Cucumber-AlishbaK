package com.saucelabs.example.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

public class PagesFactory
{
    private static ThreadLocal<PagesFactory> pagesFactories = new ThreadLocal<>();
    private RemoteWebDriver driver;

    private InventoryPage inventoryPage;
    private LoginPage loginPage;
    private CartPage cartPage;

    public static void start(RemoteWebDriver driver)
    {
        PagesFactory instance = new PagesFactory(driver);
        pagesFactories.set(instance);
    }

    public static PagesFactory getInstance()
    {
        return pagesFactories.get();
    }

    private PagesFactory(RemoteWebDriver driver)
    {
        this.driver = driver;
        inventoryPage = new InventoryPage(driver);
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);
    }

    public RemoteWebDriver getDriver()
    {
        return driver;
    }


    public InventoryPage getInventoryPage()
    {
        return inventoryPage;
    }

    public LoginPage getLoginPage()
    {
        return loginPage;
    }

    public CartPage getCartPage()
    {
        return cartPage;
    }
}
