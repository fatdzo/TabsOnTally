package com.tabsontally.markomarks.model.items;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillItem implements Serializable, Comparable<BillItem> {

    public String Title;
    public int Index;
    public String Description;
    public String Id;

    public ArrayList<VoteItem> Votes;
    public Date CreatedAt;
    public Date UpdatedAt;


    public
    BillItem()
    {
        Title = "";
        Index = 0;
        Id="none";
        Votes = new ArrayList<>();
        Description = "no description";
        CreatedAt = new Date();
        UpdatedAt = new Date();
    }

    public String getDescription()
    {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String updatedAtString = df.format(UpdatedAt);

        Description = "Updated:" + updatedAtString;
        return Description;

    }

    @Override
    public int compareTo(BillItem another) {
        int c = 0;
        /*if(c == 0)
        {
            c = Id.compareTo(another.Id);
        }

        if(c == 0)
        {
            c = Title.compareTo(another.Title);
        }*/

        if(c == 0)
        {

            c = UpdatedAt.compareTo(another.UpdatedAt);
        }

        return c;
    }

    public int getVoteIndex(VoteItem another)
    {
        for(int i=0; i< Votes.size(); i++)
        {
            if(Votes.get(i).BillId.equals(another.BillId) && Votes.get(i).PersonId.equals(another.PersonId))
            {
                return i;
            }
        }

        return -1;
    }


    public boolean containsVote(VoteItem another)
    {
        for(VoteItem vt: Votes)
        {
            if(vt.BillId.equals(another.BillId) && vt.PersonId.equals(another.PersonId))
            {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object another)
    {
        boolean areEqual = false;

        if (another != null && another instanceof BillItem)
        {
            areEqual = this.Id.equals(((BillItem) another).Id);
        }

        return areEqual;
    }

}