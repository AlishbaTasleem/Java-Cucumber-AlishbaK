package com.saucelabs.example;

public enum Browser
{
    FIREFOX("firefox"), CHROME("chrome"), SAFARI("safari"), EDGE("MicrosoftEdge"), INTERNETEXPLORER("internet explorer");

    private String browserName;

    Browser(String browserName)
    {
        this.browserName = browserName;
    }

    public Browser fromString(String browserName)
    {
        return Browser.valueOf(browserName);
    }

    public String toString()
    {
        return browserName;
    }
}
