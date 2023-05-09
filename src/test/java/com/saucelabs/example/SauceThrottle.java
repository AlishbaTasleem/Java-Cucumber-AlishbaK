package com.saucelabs.example;

public enum SauceThrottle
{
    //@formatter:off
    OFFLINE("Offline"),
    GPRS("GPRS"),
    REGULAR_2G("Regular 2G"),
    GOOD_2G("Good 2G"),
    REGULAR_3G("Regular 3G"),
    GOOD_3G("Good 3G"),
    REGULAR_4G("Regular 4G"),
    DSL("DSL"),
    WIFI("WiFi"),
    ONLINE("online");
    //@formatter:off

    private String value;

    private SauceThrottle(String value)
    {
        this.value = value;
    }

    public static SauceThrottle fromValue(String value)
    {
        return SauceThrottle.valueOf(value);
    }

    public String toValue()
    {
        return value;
    }
}
