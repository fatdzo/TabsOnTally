package com.tabsontally.markomarks.model;

import android.support.annotation.NonNull;



import java.util.List;
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
    String mBirthdate;
    String mDeathDate;

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

    public String getmBirthdate() {
        return mBirthdate;
    }

    public String getmDeathDate() {
        return mDeathDate;
    }

    public Person(@NonNull String id, @NonNull String type, String name, String sortName,
                  String givenName, String imageUrl, String gender, String summary, String identity,
                  String biography, String birthdate, String deathDate,
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