package com.saucelabs.example;

import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

public class DriverFactory implements En
{
    private static final String userName = System.getenv("SAUCE_USERNAME");
    private static final String accessKey = System.getenv("SAUCE_ACCESS_KEY");
    private static final String headlessUserName = System.getenv("SAUCE_HEADLESS_USERNAME");
    private static final String headlessAccessKey = System.getenv("SAUCE_HEADLESS_ACCESS_KEY");

    private static URL SAUCE_EU_URL;
    private static URL SAUCE_US_URL;
    private static URL HEADLESS_URL;

    static
    {


        try
        {
            SAUCE_US_URL = new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub");
        }
        catch (MalformedURLException e)
        {
            System.err.printf("Malformed SAUCE_US_URL: %s\n", e.getMessage());
            System.exit(-1);
        }

        try
        {
            SAUCE_EU_URL = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
        }
        catch (MalformedURLException e)
        {
            System.err.printf("Malformed SAUCE_EU_URL: %s\n", e.getMessage());
            System.exit(-1);
        }


        try
        {
            HEADLESS_URL = new URL("http://ondemand.us-east-1.saucelabs.com/wd/hub");
        }
        catch (MalformedURLException e)
        {
            System.err.printf("Malformed HEADLESS_URL: %s\n", e.getMessage());
            System.exit(-1);
        }
    }

    public static RemoteWebDriver getDriverInstance(TestPlatform tp, Scenario scenario)
    {
        RemoteWebDriver driver = null;

        String platform = tp.getPlatformName();
        if (tp.getPlatformContainer() == PlatformContainer.HEADLESS)
        {
            driver = getHeadlessDriverInstance(tp, scenario);
        }
        else if (platform.startsWith("Windows ") || platform.startsWith("macOS ") || platform.startsWith("OS X") || platform.equalsIgnoreCase("linux"))
        {
            driver = getDesktopDriverInstance(tp, scenario);
        }

        return driver;
    }

    private static RemoteWebDriver getHeadlessDriverInstance(TestPlatform tp, Scenario scenario)
    {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("browserName", tp.getBrowser().toString());
        caps.setCapability("version", tp.getBrowserVersion());
        caps.setCapability("platform", tp.getPlatformName());
        caps.setCapability("name", scenario.getName());

        caps.setCapability("username", headlessUserName);
        caps.setCapability("accesskey", headlessAccessKey);

        addJenkinsBuildInfo(caps);

        RemoteWebDriver driver = new RemoteWebDriver(HEADLESS_URL, caps);

        String sessionId = driver.getSessionId().toString();
        Util.log("Started %s", new Date().toString());
        Util.log("Test Results: https://app.us-east-1.saucelabs.com/tests/%s", sessionId);
        Util.log("SauceOnDemandSessionID=%s job-name=%s", sessionId, scenario.getName());

        // Set reasonable page load and script timeouts
//        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
//        driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);

        return driver;
    }

    private static RemoteWebDriver getDesktopDriverInstance(TestPlatform tp, Scenario scenario)
    {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("browserName", tp.getBrowser().toString());
        caps.setCapability("version", tp.getBrowserVersion());
        caps.setCapability("platform", tp.getPlatformName());
        String resultsURL = "";

        // Set ACCEPT_SSL_CERTS  variable to true
        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        RemoteWebDriver driver = null;
        if (Util.runLocal)
        {
            switch (tp.getBrowser())
            {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--ignore-certificate-errors");
                    HashMap<String, Object> googOpts = new HashMap<String, Object>();
                    googOpts.put("w3c", true);
                    chromeOptions.setCapability("goog:chromeOptions", googOpts);
                    chromeOptions.merge(caps);
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case EDGE:
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.merge(caps);
                    driver = new EdgeDriver(edgeOptions);
                    break;

                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setCapability("marionette", false);
                    firefoxOptions.merge(caps);
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case INTERNETEXPLORER:
                    InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                    ieOptions.merge(caps);
                    driver = new InternetExplorerDriver(ieOptions);
                    break;

                case SAFARI:
                    SafariOptions safariOptions = new SafariOptions();
                    safariOptions.merge(caps);
                    driver = new SafariDriver(safariOptions);
                    break;

                default:
                    throw new RuntimeException("Unsupported browserName: " + tp.getBrowser());
            }
            driver.manage().window().maximize();
        }
        else
        {
            // Build the Sauce Options first...
            MutableCapabilities sauceOpts = new MutableCapabilities();
            sauceOpts.setCapability("name", scenario.getName());
            sauceOpts.setCapability("username", userName);
            sauceOpts.setCapability("accesskey", accessKey);
            sauceOpts.setCapability("recordVideo", "true");
            sauceOpts.setCapability("recordMp4", "true");
            sauceOpts.setCapability("recordScreenshots", "true");
//            sauceOpts.setCapability("screenResolution", "1600x1200");

            if (tp.getExtendedDebugging())
            {
                sauceOpts.setCapability("extendedDebugging", true);
            }


//            sauceOpts.setCapability("seleniumVersion", "3.12.0");

            // Add Jenkins Build Info...
            addJenkinsBuildInfo(sauceOpts);

//            if (tp.getPlatformName().equalsIgnoreCase("linux"))
            {
                // Presently, no supported browsers on Sauce Labs' Linux have W3C so we default back to the old driver
                caps.merge(sauceOpts);
            }

            if (tp.getDataCenter().equals(DataCenter.US))
            {
                driver = new RemoteWebDriver(SAUCE_US_URL, caps);
                resultsURL = "https://app.saucelabs.com/tests";
            }
            else
            {
                driver = new RemoteWebDriver(SAUCE_EU_URL, caps);
                resultsURL = "https://app.eu-central-1.saucelabs.com/tests";
            }
        }

        String sessionId = driver.getSessionId().toString();
        Util.log("Started %s", new Date().toString());
        Util.log("Test Results: %s/%s", resultsURL, sessionId);
        Util.log("SauceOnDemandSessionID=%s job-name=%s", sessionId, scenario.getName());

        // Set reasonable page load and script timeouts
//        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
//        driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);

        return driver;
    }

    private static MutableCapabilities addJenkinsBuildInfo(MutableCapabilities sauceOpts)
    {
        // Pull the Job Name and Build Number from Jenkins if available...
        String jenkinsBuildNumber = System.getenv("JENKINS_BUILD_NUMBER");
        if (jenkinsBuildNumber != null)
        {
            sauceOpts.setCapability("build", jenkinsBuildNumber);
        }
        else
        {
            String jobName = System.getenv("JOB_NAME");
            String buildNumber = System.getenv("BUILD_NUMBER");

            if (jobName != null && buildNumber != null)
            {
                sauceOpts.setCapability("build", String.format("%s__%s", jobName, buildNumber));
            }
            else
            {
                sauceOpts.setCapability("build", Util.buildTag);
            }
        }

        return sauceOpts;
    }

}
