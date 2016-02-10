package com.tabsontally.markomarks.model;

import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 1/19/2016.
 */
public class Vote implements Comparable<Vote> {

    private String mId;
    private String mResult;
    private String mBillId;
    private String mPersonId;
    private String mPersonName;
    private Date mUpdated;
    private LegislatorVotingOption mPersonVoteOption;

    private Relationship mVoteRelationShip;

    public Relationship getmVoteRelationShip() {
        return mVoteRelationShip;
    }

    public String getmId() {
        return mId;
    }

    public String getmResult() {
        return mResult;
    }

    public LegislatorVotingOption getmPersonVoteOption() {
        return mPersonVoteOption;
    }

    public void setmPersonVoteOption(LegislatorVotingOption mPersonVoteOption) {
        this.mPersonVoteOption = mPersonVoteOption;
    }

    public String getmBillId() {
        return mBillId;
    }

    public void setmBillId(String mBillId) {
        this.mBillId = mBillId;
    }

    public String getmPersonId() {
        return mPersonId;
    }

    public void setmPersonId(String mPersonId) {
        this.mPersonId = mPersonId;
    }

    public void setmPersonName(String personName)
    {
        this.mPersonName = personName;
    }

    public String getmPersonName()
    {
        return mPersonName;

    }



    public Date getmUpdated() {
        return mUpdated;
    }


    public Vote(String id, String result, Date updated, Relationship relationsShip)
    {
        mId = id;
        mResult = result;
        mUpdated = updated;
        mVoteRelationShip = relationsShip;

        mBillId = mVoteRelationShip.getmBillId();
    }

    @Override
    public int compareTo(Vote o) {
        int c = 0;
        if(c == 0)
        {
            c = getmPersonId().compareTo(o.getmPersonId());
        }

        if(c == 0)
        {
            c = getmBillId().compareTo(o.getmBillId());
        }

        if(c == 0)
        {
            c = getmUpdated().compareTo(o.getmUpdated());
        }

        return c;
    }

}
