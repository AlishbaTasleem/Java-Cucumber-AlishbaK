package com.saucelabs.example.stepDefinitions;

import com.saucelabs.example.Util;
import com.saucelabs.example.pages.InventoryPage;
import com.saucelabs.example.pages.PagesFactory;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

public class InventoryPageSteps implements En
{
    public InventoryPageSteps()
    {

        And("^The user clicks on the shopping cart$", () -> {
            PagesFactory pf = PagesFactory.getInstance();
            RemoteWebDriver driver = pf.getDriver();
            Util.info(driver, ">>> The user clicks on the shopping cart");

            InventoryPage inventoryPage = pf.getInventoryPage();
            inventoryPage.clickOnShoppingCart();
        });

        And("^The user chooses a \"([^\"]*)\" by clicking 'Add To Cart'$", (String itemName) -> {
            PagesFactory pf = PagesFactory.getInstance();
            RemoteWebDriver driver = pf.getDriver();
            Util.info(driver, ">>> The user chooses a \"%s\" by clicking 'Add To Cart'", itemName);

            InventoryPage inventoryPage = pf.getInventoryPage();
            inventoryPage.addItemToCartByName(itemName);
        });

        And("^The user selects$", (DataTable dataTable) -> {
            PagesFactory pf = PagesFactory.getInstance();
            RemoteWebDriver driver = pf.getDriver();

            InventoryPage inventoryPage = pf.getInventoryPage();

            List<String> selectedItems = dataTable.asList(String.class);
            for (String itemName : selectedItems)
            {
                Util.info(driver, ">>> The user selects \"%s\"", itemName);
                inventoryPage.addItemToCartByName(itemName);
            }
        });
    }
}
