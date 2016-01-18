package com.tabsontally.markomarks.model;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by johnli on 1/16/16.
 */
public class Bill extends BaseData implements Comparable<Bill>{

    public Bill(@NonNull String id, @NonNull String type) {
        super(id, type);
    }


    public Bill(@NonNull String id, @NonNull String type, String billId, String title, String legislativeName,
                String legislativeSessionId, String[] classification, String[] subjects, Date created, Date updated) {
        super(id, type);
        mBillId = billId;
        mTitle = title;
        mLegislativeSessionId = legislativeSessionId;
        mLegislativeSessionName = legislativeName;
        mClassification = classification;
        mSubjects = subjects;
        mUpdated = updated;
        mCreated = created;

    }

    public String getmBillId() {
        return mBillId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmLegislativeSessionName() {
        return mLegislativeSessionName;
    }

    public String getmLegislativeSessionId() {
        return mLegislativeSessionId;
    }

    public String[] getmClassification() {
        return mClassification;
    }

    public String[] getmSubjects() {
        return mSubjects;
    }

    public Date getmUpdated(){
        return mUpdated;
    }

    public Date getmCreated() {return mCreated; }

    public int compareTo(Bill compareBill) {
        Date compareUpdate = compareBill.getmUpdated();
        return compareUpdate.compareTo(mUpdated);
    }

    String mBillId;
    String mTitle;
    String mLegislativeSessionName;
    String mLegislativeSessionId;
    String[] mClassification;
    String[] mSubjects;
    Date mUpdated;
    Date mCreated;

}
