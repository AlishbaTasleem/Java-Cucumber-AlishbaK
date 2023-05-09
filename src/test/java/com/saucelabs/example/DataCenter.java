package com.saucelabs.example;

public enum DataCenter
{
    US("us"), EU("eu");

    private String dcName;

    DataCenter(String dcName)
    {
        this.dcName = dcName;
    }

    public DataCenter fromString(String dcName)
    {
        return DataCenter.valueOf(dcName);
    }

    public String toString()
    {
        return dcName;
    }

    public boolean equals(DataCenter dc)
    {
        return dcName.equalsIgnoreCase(dc.dcName);
    }
}
