package com.tabsontally.markomarks.model;

import com.tabsontally.markomarks.model.minor.LinkObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 2/15/2016.
 */
public class VersionObject {
    private String mNote;
    private Date mDate;
    private ArrayList<LinkObject> mLinks;

    public VersionObject(String note, Date date, ArrayList<LinkObject> links)
    {
        mNote = note;
        mDate = date;
        mLinks = links;
    }

    public String getNote() {
        return mNote;
    }

    public Date getDate() {
        return mDate;
    }

    public ArrayList<LinkObject> getLinks() {
        return mLinks;
    }

}
