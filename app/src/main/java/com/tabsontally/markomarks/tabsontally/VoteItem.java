package com.tabsontally.markomarks.tabsontally;

import com.tabsontally.markomarks.RouteManager.LegislatorVotingOption;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class VoteItem  implements Serializable{
    public int Index;
    public String Id;
    public String Name;
    public String Description;
    public String Result;
    public String BillId;
    public String PersonId;
    //how did the legislator vote, true == YES, false == NO
    public LegislatorVotingOption PersonVotingOption;
    public Date Updated;

    public String getPersonVotedString()
    {
        if(PersonVotingOption == LegislatorVotingOption.YES)
        {
            return "YES";
        }
        if(PersonVotingOption == LegislatorVotingOption.NO)
        {
            return "NO";
        }
        if(PersonVotingOption == LegislatorVotingOption.NOTVOTING)
        {
            return "NOT VOTING";
        }
        return "UNKNOWN";
    }


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

    @Override
    public boolean equals(Object another)
    {
        boolean isEqual= false;

        if (another != null && another instanceof VoteItem)
        {
            isEqual = (this.PersonId.equals(((VoteItem) another).PersonId)) && (this.BillId.equals(((VoteItem) another).BillId));
        }

        return isEqual;
    }
}
