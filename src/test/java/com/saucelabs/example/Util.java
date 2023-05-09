package com.saucelabs.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Util
{
    /**
     * If true, the tests will be run on the local desktop.  If false, the tests will run on Sauce Labs.
     */
    public static final boolean runLocal = false;
    public static final boolean useUnifiedPlatform = false;

    public static final String buildTag = "Build " + new Date();

//    public static boolean isDesktop;
//    public static boolean isMobile;
//    public static boolean isEmuSim;
//    public static boolean isHeadless;

    private static ThreadLocal<TestPlatform> testPlatformThreadLocal = new ThreadLocal<>();

    /**
     * Puts a Sauce breakpoint in the test. Test execution will pause at this point, waiting for manual control
     * by clicking in the test’s live video.  A space must be included between sauce: and break.
     *
     * @param driver The WebDriver instance we use to execute the Javascript command
     */
    public static void breakpoint(WebDriver driver)
    {
        ((JavascriptExecutor) driver).executeScript("sauce: break");
    }

    /**
     * Logs the given line in the job’s Selenium commands list. No spaces can be between sauce: and context.
     *
     * @param driver The WebDriver instance we use to log the info
     */
    public static void info(WebDriver driver, String format, Object... args)
    {
        System.out.printf(format, args);
        System.out.println();

        PlatformContainer pc = getTestPlatform().getPlatformContainer();

        switch (pc)
        {
            case DESKTOP:
            case EMULATOR:
            case SIMULATOR:
            case HEADLESS:
                break;

            default:
                // All others... not supported.
                return;
        }

        String msg = String.format(format, args);
        ((JavascriptExecutor) driver).executeScript("sauce:context=" + msg);
    }

    /**
     * Sets the job name
     *
     * @param driver The WebDriver instance we use to log the info
     */
    public static void name(WebDriver driver, String format, Object... args)
    {
        System.out.printf(format, args);
        System.out.println();

        PlatformContainer pc = getTestPlatform().getPlatformContainer();

        switch (pc)
        {
            case DESKTOP:
            case EMULATOR:
            case SIMULATOR:
            case HEADLESS:
                break;

            default:
                // All others... not supported.
                return;
        }

        String msg = String.format(format, args);
        ((JavascriptExecutor) driver).executeScript("sauce:job-name=" + msg);
    }

    public static void reportSauceLabsResult(WebDriver driver, boolean status)
    {
        PlatformContainer pc = getTestPlatform().getPlatformContainer();

        switch (pc)
        {
            case DESKTOP:
            case EMULATOR:
            case SIMULATOR:
            case HEADLESS:
                break;

            default:
                // All others... not supported.
                return;
        }

        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + status);
    }

    /**
     * Uses the Appium V2 RESTful API to report test result status to the Sauce Labs dashboard.
     *
     * @param sessionId The session ID we want to set the status for
     * @param status    TRUE if the test was successful, FALSE otherwise
     */


    public static void log(String format, Object... args)
    {
        System.out.printf(format, args);
        System.out.println();
    }

    public static void sauceThrottle(WebDriver driver, SauceThrottle condition)
    {
        PlatformContainer pc = getTestPlatform().getPlatformContainer();

        switch (pc)
        {
            case DESKTOP:
                break;

            default:
                // All others... not supported.
                return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("condition", condition.toValue());
        try
        {
            ((JavascriptExecutor) driver).executeScript("sauce:throttle", map);
        }
        catch (JavascriptException e)
        {
            RemoteWebDriver rwd = (RemoteWebDriver) driver;
            Capabilities caps = rwd.getCapabilities();
            System.err.printf(">>> Failed to set Sauce Throttle: %s\n(%s %s on %s)\n", e.getMessage());
            System.err.printf(">>> (%s %s on %s)\n", caps.getBrowserName(), caps.getVersion(), caps.getPlatform());
        }
    }
    public static void sleep(long msecs)
    {
        try
        {
            Thread.sleep(msecs);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void takeScreenShot(WebDriver driver)
    {
        PlatformContainer pc = getTestPlatform().getPlatformContainer();

        switch (pc)
        {
            case DESKTOP:
            case EMULATOR:
            case SIMULATOR:
            case HEADLESS:
                break;

            default:
                // All others... not supported.
                return;
        }

        WebDriver augDriver = new Augmenter().augment(driver);
        File file = ((TakesScreenshot) augDriver).getScreenshotAs(OutputType.FILE);

        long time = new Date().getTime();
        String outputName = time + ".png";
        try
        {
            FileUtils.copyFile(file, new File(outputName));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static TestPlatform getTestPlatform()
    {
        return testPlatformThreadLocal.get();
    }

    public static void setTestPlatform(TestPlatform tp)
    {
        testPlatformThreadLocal.set(tp);
    }
}
