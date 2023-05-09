package com.saucelabs.example;

public enum PlatformContainer
{
    DESKTOP("desktop"), MOBILE("mobile"), EMULATOR("emulator"), SIMULATOR("simulator"), HEADLESS("headless");

    private String name;

    PlatformContainer(String name)
    {
        this.name = name;
    }

    public PlatformContainer fromString(String name)
    {
        return PlatformContainer.valueOf(name);
    }

    public String toString()
    {
        return name;
    }
}
