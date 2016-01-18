package com.tabsontally.markomarks.tabsontally;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillItem {

    public String Title;
    public int Index;
    public String Description;
    public String Id;

    public String Vote;
    public Date CreatedAt;
    public Date UpdatedAt;

    public
    BillItem()
    {
        Title = "";
        Index = 0;
        Id="none";
        Vote = "Fail";
        Description = "";
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
