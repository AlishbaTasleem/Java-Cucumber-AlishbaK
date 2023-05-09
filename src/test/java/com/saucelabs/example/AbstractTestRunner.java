package com.saucelabs.example;

import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import gherkin.events.PickleEvent;
import gherkin.pickles.Pickle;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public abstract class AbstractTestRunner
{
    protected TestNGCucumberRunner testNGCucumberRunner;

    @AfterClass(alwaysRun = true)
    public void tearDownClass()
    {
        if (testNGCucumberRunner == null)
        {
            return;
        }

        testNGCucumberRunner.finish();
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void feature(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper cucumberFeatureWrapper)
    throws Throwable
    {
        PickleEvent event = pickleWrapper.getPickleEvent();
        Pickle pickle = event.pickle;
        System.out.printf("Running scenario: %s\n", pickle.getName());

        // the 'featureWrapper' parameter solely exists to display the feature file in a test report
        testNGCucumberRunner.runScenario(event);
    }

    @DataProvider
    public Object[][] scenarios()
    {
        if (testNGCucumberRunner == null)
        {
            return null;
        }

        return testNGCucumberRunner.provideScenarios();
    }
}
