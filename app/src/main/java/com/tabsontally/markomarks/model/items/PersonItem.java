package com.tabsontally.markomarks.model.items;

import android.graphics.drawable.Drawable;

import com.tabsontally.markomarks.model.PersonDetails;

import java.io.Serializable;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class PersonItem implements Serializable {
    public int Index;
    public String Id;
    public String FullName;
    public String ImageUrl;
    public PersonDetails Details;

    public PersonItem()
    {
        Index = 0;
        Id = "none";
        FullName = "";
        ImageUrl = "";
    }
}

