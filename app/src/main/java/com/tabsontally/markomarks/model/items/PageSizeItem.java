package com.tabsontally.markomarks.model.items;

/**
 * Created by MarkoPhillipMarkovic on 2/7/2016.
 */
public class PageSizeItem extends BaseItem {

    private String mText;

    public PageSizeItem(int value)
    {
        super(value);
    }

    @Override
    public String getmText()
    {
        return String.valueOf(mValue) + " results";

    }

}
