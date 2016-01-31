package com.tabsontally.markomarks.tabsontally;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillItem implements Serializable{

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

}
