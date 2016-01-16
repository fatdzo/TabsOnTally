package com.tabsontally.markomarks.tabsontally;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillVoteItem {

    public String LastName;
    public String FirstName;
    public String FullName;
    public int Index;
    public String Id;
    public String Vote;

    public BillVoteItem()
    {
        FirstName= "FirstName";
        LastName = "LastName";
        FullName = LastName + "," + FirstName;
        Index = 0;
        Id = "dfakjhsdfoiuwe";
        Vote = "Not voted";

    }

}
