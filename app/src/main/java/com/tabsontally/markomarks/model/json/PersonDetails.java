package com.tabsontally.markomarks.model.json;

import com.tabsontally.markomarks.model.minor.ContactDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 2/11/2016.
 */
public class PersonDetails extends BaseData implements Serializable{
    ArrayList<ContactDetail> mContactDetails;
    String mImageUrl;
    String mName;
    String mSortName;
    String mFamilyName;
    String mGivenName;
    String mGender;
    String mSummary;
    String mNationalIdentity;
    String mBiography;
    Date mBirthDate;
    Date mDeathDate;


    public PersonDetails(String id,
                         String type,
                         String name,
                         String sortName,
                         String familyName,
                         String givenName,
                         String gender,
                         String summary,
                         String nationalIdentity,
                         String biography,
                         Date birthDate,
                         Date deathDate,
                         ArrayList<ContactDetail> contactDetails,
                         String imageUrl)
    {
        super(id,type);
        mName = name;
        mSortName = sortName;
        mFamilyName = familyName;
        mGivenName = givenName;
        mGender = gender;
        mSummary = summary;
        mNationalIdentity = nationalIdentity;
        mBiography = biography;
        mContactDetails = contactDetails;
        mBirthDate = birthDate;
        mDeathDate = deathDate;
        mImageUrl = imageUrl;
    }


    public ArrayList<ContactDetail> getmContactDetails() {
        return mContactDetails;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public String getmSortName() {
        return mSortName;
    }

    public String getmFamilyName() {
        return mFamilyName;
    }

    public String getmGivenName() {
        return mGivenName;
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

    public Date getmBirthDate() {
        return mBirthDate;
    }

    public Date getmDeathDate() {
        return mDeathDate;
    }

    @Override
    public boolean equals(Object another)
    {
        boolean areEqual = false;

        if (another != null && another instanceof PersonDetails)
        {
            areEqual = this.getId().equals(((PersonDetails) another).getId());
        }

        return areEqual;
    }
}
