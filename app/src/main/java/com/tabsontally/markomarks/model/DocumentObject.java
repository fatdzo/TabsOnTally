package com.tabsontally.markomarks.model;

import com.tabsontally.markomarks.model.minor.LinkObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 1/24/2016.
 */
public class DocumentObject {

    private String mNote;
    private ArrayList<LinkObject> mLinks;
    private Date mDate;

    public DocumentObject(String note, Date date, ArrayList<LinkObject> links)
    {

        mNote = note;
        mDate = date;
        mLinks = links;

    }

    public String getmNote() {
        return mNote;
    }

    public ArrayList<LinkObject> getmLinks() {
        return mLinks;
    }

    public Date getmDate() {
        return mDate;
    }

}
