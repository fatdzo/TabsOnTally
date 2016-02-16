package com.tabsontally.markomarks.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 1/24/2016.
 */
public class BillDetail extends BaseData {

    private String mTitle;
    private Date mUpdatedAt;
    private Date mCreatedAt;
    private String mIdentifier;
    private ArrayList<String> mSubjects;
    private ArrayList<DocumentObject> mDocuments;

    ArrayList<VersionObject> mVersions;

    public BillDetail(String id, String type, String title, Date createdAt, Date updatedAt, String identifier, ArrayList<String> subjects, ArrayList<DocumentObject> documents, ArrayList<VersionObject> versions) {
        super(id, type);
        mTitle = title;
        mCreatedAt = createdAt;
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

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public String getDescription()
    {
        String result = "";

        //Give me the latest version and the latest link
        if(getVersions().size() > 0)
        {
            VersionObject latestVersion = getVersions().get(0);
            if(latestVersion.getLinks().size() > 0)
            {
                result += latestVersion.getLinks().get(0).getText();

            }


        }

        return result;
    }

    public String getSubjectsString()
    {
        String result = "subjects: ";
        for(String sub:getSubjects())
        {
            result += sub + ", ";
        }

        Log.e("TABSONTALLY", "GET SUBJECTS STRING ->" + result);
        if(result.length() > 2)
        {
            return result.substring(0, result.length() - 2);
        }
        return result;
    }

    public String getDatesString()
    {
        String result = "";

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        if(getCreatedAt()!=null) {
            result += "created: " + formatter.format(getCreatedAt());
        }

        if(getUpdatedAt()!=null)
        {
            if(getCreatedAt()!=null)
            {
                result += "     ";
            }
            result += "updated: " + formatter.format(getUpdatedAt());
        }

        return result;
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