package com.tabsontally.markomarks.model;

import com.tabsontally.markomarks.model.minor.ContactDetail;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 2/11/2016.
 */
public class PersonDetails implements Serializable{
    public ArrayList<ContactDetail> getmContactDetails() {
        return mContactDetails;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    ArrayList<ContactDetail> mContactDetails;
    String mImageUrl;


    public PersonDetails(String id, String type, ArrayList<ContactDetail> contactDetails, String imageUrl)
    {
        mContactDetails = contactDetails;
        mImageUrl = imageUrl;
    }

}
