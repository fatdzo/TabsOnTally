package com.tabsontally.markomarks.model;

/**
 * Created by MarkoPhillipMarkovic on 2/3/2016.
 */
public class Relationship {

    private String mOrganizationId;
    private String mOrganizationType;
    private String mOrganizationName;
    private String mBillId;
    private String mBillType;
    private String mBillName;

    public String getmOrganizationId() {
        return mOrganizationId;
    }

    public String getmOrganizationType() {
        return mOrganizationType;
    }

    public String getmBillId() {
        return mBillId;
    }

    public String getmBillType() {
        return mBillType;
    }

    public Relationship(String organizationId, String organizationType, String billId, String billType)
    {
        mOrganizationId = organizationId;
        mOrganizationType = organizationType;
        mBillId = billId;
        mBillType = billType;
    }
}