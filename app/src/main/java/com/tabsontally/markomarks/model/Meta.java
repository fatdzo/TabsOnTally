package com.tabsontally.markomarks.model;

import android.support.annotation.NonNull;

/**
 * Created by MarkoPhillipMarkovic on 1/19/2016.
 */
public class Meta {
    public int Page = 1;
    public int Pages = 1;
    public int Count = 0;


    public Meta(int page, int pages, int count)
    {
        Page = page;
        Pages = pages;
        Count = count;
    }
}
