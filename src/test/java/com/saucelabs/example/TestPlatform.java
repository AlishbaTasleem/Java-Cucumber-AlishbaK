package com.saucelabs.example;

public class TestPlatform
{
    private Browser browser;
    private DataCenter dataCenter;
    private PlatformContainer platformContainer;
    private String appKey;
    private String browserVersion;
    private String deviceName;
    private String platformName;
    private String platformVersion;
    private Boolean extendedDebugging;


    private TestPlatform()
    {
    }

    public String getAppKey()
    {
        return appKey;
    }

    public Browser getBrowser()
    {
        return browser;
    }

    public String getBrowserVersion()
    {
        return browserVersion;
    }


    public DataCenter getDataCenter()
    {
        return dataCenter;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public Boolean getExtendedDebugging() { return extendedDebugging; }

    public PlatformContainer getPlatformContainer()
    {
        return platformContainer;
    }

    public String getPlatformName()
    {
        return platformName;
    }

    public String getPlatformVersion()
    {
        return platformVersion;
    }

    public static class Builder
    {
        private Browser browser;
        private String browserVersion;
        private String platformName;
        private String platformVersion;
        private PlatformContainer platformContainer;
        private String deviceName;
        private DataCenter dataCenter;
        private String appKey;
        private Boolean extendedDebugging;

        public Builder()
        {

        }

        public Builder appKey(String appKey)
        {
            this.appKey = appKey;
            return this;
        }

        public Builder browser(Browser browser)
        {
            this.browser = browser;
            return this;
        }

        public Builder browserVersion(String browserVersion)
        {
            this.browserVersion = browserVersion;
            return this;
        }


        public Builder dataCenter(DataCenter dataCenter)
        {
            this.dataCenter = dataCenter;
            return this;
        }

        public Builder deviceName(String deviceName)
        {
            this.deviceName = deviceName;
            return this;
        }

        public Builder extendedDebugging(Boolean extendedDebugging)
        {
            this.extendedDebugging = extendedDebugging;
            return this;
        }

        public Builder platformName(String platformName)
        {
            this.platformName = platformName;
            return this;
        }

        public Builder platformVersion(String platformVersion)
        {
            this.platformVersion = platformVersion;
            return this;
        }

        public Builder platformContainer(PlatformContainer platformContainer)
        {
            this.platformContainer = platformContainer;
            return this;
        }

        public TestPlatform build()
        {
            TestPlatform tp = new TestPlatform();
            tp.appKey = appKey;
            tp.browser = browser;
            tp.browserVersion = browserVersion;
            tp.dataCenter = dataCenter;
            tp.deviceName = deviceName;
            tp.extendedDebugging = extendedDebugging;
            tp.platformContainer = platformContainer;
            tp.platformName = platformName;
            tp.platformVersion = platformVersion;
            return tp;
        }
    }

}
