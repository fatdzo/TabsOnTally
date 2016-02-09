package com.tabsontally.markomarks.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 1/24/2016.
 */
public class BillDetail extends BaseData {

    private String mTitle;
    private Date mUpdatedAt;
    private String mIdentifier;

    private ArrayList<String> mSubjects;
    private ArrayList<DocumentObject> mDocuments;

    public BillDetail(String id, String type, String title, Date updatedAt, String identifier, ArrayList<String> subjects, ArrayList<DocumentObject> documents) {
        super(id, type);
        mTitle = title;
        mUpdatedAt = updatedAt;
        mIdentifier = identifier;
        mSubjects = subjects;
        mDocuments = documents;

    }

    public String getmTitle() {
        return mTitle;
    }

    public Date getmUpdatedAt() {
        return mUpdatedAt;
    }

    public String getmIdentifier() {
        return mIdentifier;
    }

    public ArrayList<String> getmSubjects() {
        return mSubjects;
    }

    public ArrayList<DocumentObject> getmDocuments() {
        return mDocuments;


    }
}