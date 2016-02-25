package com.tabsontally.markomarks.model.json;

import android.support.annotation.NonNull;


import com.tabsontally.markomarks.model.json.BaseData;

import java.util.Date;
import java.util.Map;

public class Person extends BaseData {

    String mName;
    String mSortName;
    String mGivenName;
    String mImageUrl;
    String mGender;
    String mSummary;
    String mNationalIdentity;
    String mBiography;
    Date mBirthdate;
    Date mDeathDate;

    Map<String, String> mExtra;


    public String getmName() {
        return mName;
    }

    public String getmSortName() {
        return mSortName;
    }

    public String getmGivenName() {
        return mGivenName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmGender() {
        return mGender;
    }

    public String getmSummary() {
        return mSummary;
    }

    public String getmNationalIdentity() {
        return mNationalIdentity;
    }

    public String getmBiography() {
        return mBiography;
    }

    public Date getmBirthdate() {
        return mBirthdate;
    }

    public Date getmDeathDate() {
        return mDeathDate;
    }

    public Person(@NonNull String id, @NonNull String type, String name, String sortName,
                  String givenName, String imageUrl, String gender, String summary, String identity,
                  String biography, Date birthdate, Date deathDate,
                  Map<String, String> extra) {
        super(id, type);
        mName = name;
        mSortName = sortName;
        mGivenName = givenName;
        mImageUrl = imageUrl;
        mGender = gender;
        mSummary = summary;

        mNationalIdentity = identity;
        mBiography = biography;
        mBirthdate = birthdate;
        mDeathDate = deathDate;
        mExtra = extra;
    }
}