package com.tabsontally.markomarks.model;

import com.tabsontally.markomarks.RouteManager.LegislatorVotingOption;

import java.util.Date;

/**
 * Created by MarkoPhillipMarkovic on 1/19/2016.
 */
public class Vote implements Comparable<Vote> {

    public String getmId() {
        return mId;
    }

    public String getmResult() {
        return mResult;
    }

    private String mId;
    private String mResult;

    public LegislatorVotingOption getmPersonVoteOption() {
        return mPersonVoteOption;
    }

    public void setmPersonVoteOption(LegislatorVotingOption mPersonVoteOption) {
        this.mPersonVoteOption = mPersonVoteOption;
    }

    private LegislatorVotingOption mPersonVoteOption;

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

    private String mBillId;
    private String mPersonId;

    public Date getmUpdated() {
        return mUpdated;
    }

    private Date mUpdated;

    public Vote(String id, String result, Date updated)
    {
        mId = id;
        mResult = result;
        mUpdated = updated;

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
