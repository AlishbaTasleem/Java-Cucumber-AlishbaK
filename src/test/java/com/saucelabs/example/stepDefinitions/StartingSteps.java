package com.saucelabs.example.stepDefinitions;

import com.saucelabs.example.*;
import com.saucelabs.example.pages.PagesFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Date;

public class StartingSteps extends DriverFactory implements En
{
    private RemoteWebDriver driver;
    private Date startDate, stopDate;

    public StartingSteps()
    {
        Before((Scenario scenario) -> {

            startDate = new Date();

            TestPlatform tp = Util.getTestPlatform();

            // When running in Intellij as a Cucumber Test (not via TestNG or Maven), the AbstractTestRunner.beforeClass()
            // won't get called and that's where we get out Test Platform info from.  So we set a default test platform
            // in these cases.
            if (tp == null)
            {
                TestPlatform.Builder builder = new TestPlatform.Builder();

                // @formatter:off

                // Sample Window/Chrome test
                tp = builder
                        .browser(Browser.CHROME)
                        .browserVersion("latest")
                        .platformName("Windows 10")
                        .dataCenter(DataCenter.US)
                        .platformContainer(PlatformContainer.DESKTOP)
                        .build();

//                // Sample Headless/Chrome test
//                tp = builder
//                        .browser(Browser.CHROME)
//                        .browserVersion("latest")
//                        .platformName("Linux")
//                        .dataCenter(DataCenter.US)
//                        .platformContainer(PlatformContainer.HEADLESS)
//                        .build();

                // @formatter:on
                Util.setTestPlatform(tp);
            }

            driver = DriverFactory.getDriverInstance(tp, scenario);
            PagesFactory.start(driver);
        });

        After((Scenario scenario) -> {
            boolean isSuccess = !scenario.isFailed();

            stopDate = new Date();
            Util.log("Completed %s, %d seconds.", stopDate, (stopDate.getTime() - startDate.getTime()) / 1000L);

            if (driver == null)
            {
                return;
            }

            // For now, report test status to both SL and TO and let the Util methods determine which is appropriate...
            Util.reportSauceLabsResult(driver, isSuccess);

            driver.quit();
        });

    }

    @Before("@Signup-DataDriven")
    public void signupSetup()
    {
        System.out.println("This should run everytime before any of the @Signup-DataDriven tagged scenario is going to run");
    }

    @After("@Signup-DataDriven")
    public void signupTeardown()
    {
        System.out.println("This should run everytime after any of the @Signup-DataDriven tagged scenario has run");
    }
}
