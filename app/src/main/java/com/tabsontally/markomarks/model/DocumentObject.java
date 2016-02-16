package com.tabsontally.markomarks.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 1/24/2016.
 */
public class DocumentObject {
    public String getmNote() {
        return mNote;
    }

    public ArrayList<String> getmLinks() {
        return mLinks;
    }

    public Date getmDate() {
        return mDate;
    }

    private String mNote;
    private ArrayList<String> mLinks;
    private Date mDate;

    public DocumentObject(String note, Date date, ArrayList<String> links)
    {

        mNote = note;
        mDate = date;
        mLinks = links;

    }

}
