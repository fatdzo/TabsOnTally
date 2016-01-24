package com.tabsontally.markomarks.tabsontally;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillVoteItem {

    public String LastName;
    public String FirstName;
    public String FullName;
    public int Index;
    public String Id;
    public ArrayList<VoteItem> Votes;

    public BillVoteItem()
    {
        FirstName= "FirstName";
        LastName = "LastName";
        FullName = LastName + "," + FirstName;
        Index = 0;
        Id = "none";
        Votes = new ArrayList<>();

    }

}
