package com.tabsontally.markomarks.model.items;

import com.tabsontally.markomarks.model.LegislatorVotingOption;

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
