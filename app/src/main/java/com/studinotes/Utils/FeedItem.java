package com.studinotes.Utils;

public class FeedItem {
    private String NAME;
    private String ColorCode;
    public FeedItem(String NAME,String ColorCode)
    {
        this.ColorCode=ColorCode;
        this.NAME=NAME;
    }
    public String getNAME()
    {
        return NAME;
    }
    public void setNAME (String NAME)
    {
        this.NAME=NAME;
    }
    public String getColorCode()
    {
        return ColorCode;
    }
    public void setMAIL(String MAIL)
    {
        this.ColorCode=ColorCode;
    }
}