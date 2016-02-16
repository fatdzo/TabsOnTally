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

    ArrayList<VersionObject> mVersions;

    public BillDetail(String id, String type, String title, Date updatedAt, String identifier, ArrayList<String> subjects, ArrayList<DocumentObject> documents, ArrayList<VersionObject> versions) {
        super(id, type);
        mTitle = title;
        mUpdatedAt = updatedAt;
        mIdentifier = identifier;
        mSubjects = subjects;
        mDocuments = documents;
        mVersions = versions;

    }

    public String getmTitle() {
        return mTitle;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    public ArrayList<String> getSubjects() {
        return mSubjects;
    }

    public ArrayList<VersionObject> getVersions() {
        return mVersions;
    }

    public ArrayList<DocumentObject> getDocuments() {
        return mDocuments;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof BillDetail)
        {
            sameSame = this.getId().equals(((BillDetail) object).getId());
        }

        return sameSame;
    }
}