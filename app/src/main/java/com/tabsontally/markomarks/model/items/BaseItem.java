package com.tabsontally.markomarks.model.items;

/**
 * Created by MarkoPhillipMarkovic on 2/7/2016.
 */
public class BaseItem {
    private String mText;
    public int mValue;

    public BaseItem(int value, String text)
    {
        mValue = value;
        mText = text;
    }

    public BaseItem(int value)
    {
        mValue = value;
    }

    public String getmText()
    {
        return mText;

    }

    public int getmvalue()
    {
        return mValue;
    }

}
