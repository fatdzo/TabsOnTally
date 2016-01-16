package com.tabsontally.markomarks.tabsontally;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class PersonItem {
    public int Index;
    public String Id;
    public String FullName;
    public String FirstName;
    public String LastName;

    public PersonItem()
    {
        Index = 0;
        Id = "none";
        FirstName = "";
        LastName = "";

        FullName = LastName + "," + FirstName;
    }
}

