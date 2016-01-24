package com.tabsontally.markomarks.tabsontally;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class VoteItem {
    public int Index;
    public String Id;
    public String Name;
    public String Description;
    public String Result;
    public String BillId;
    public String PersonId;

    public int getResultValue()
    {
        int resValue = 0;
        String res = Result.trim();
        if(res.equals("fail"))
        {
            resValue = -1;
        }

        if(res.equals("pass"))
        {
            resValue = 1;
        }

        return resValue;
    }


    public VoteItem()
    {
        Id = "";
        Name = "";
        Description = "";
        Index = 0;
        Result = "";

        BillId = "";
        PersonId = "";
    }


}
