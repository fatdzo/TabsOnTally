package com.tabsontally.markomarks.routemanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.LegislatorVotingOption;
import com.tabsontally.markomarks.model.json.Meta;
import com.tabsontally.markomarks.model.json.Vote;
import com.tabsontally.markomarks.model.items.VoteItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by MarkoPhillipMarkovic on 1/19/2016.
 */
public class VoteManager extends BaseRouteManager {

    public static final String PULL_SUCCESS = "com.tabsontally.markomarks.votemanager.PULL SUCCESS";
    private static final String ROUTE = "votes/";

    private String mPersonId;
    private String mPersonName;
    private LegislatorVotingOption mVotingOption;
    private ArrayList<Integer> mPagesPulled = new ArrayList<>();

    Meta mMeta = new Meta(1,1,0);

    private Gson mGson;

    HashMap<String, Vote> mVotes;

    public VoteManager(Context context, APIConfig config, Gson gson) {
        super(context, config);
        mVotes = new HashMap<>();
        mUsePaging = true;
        mGson = gson;
        mInterval = 3;

        switchState(IDLE);
    }

    //legislatorVoteOption - it can be Yes, No, Not voting
    public void pullRecords(final String personId, final String personName, LegislatorVotingOption legislatorVoteOption, int startPage)
    {
        mPersonId = personId;
        mPersonName = personName;
        mVotingOption = legislatorVoteOption;
        mCurrentPage = startPage;
        mStartPage = startPage;
        switchState(PULLING);
        pullRecordStep(mCurrentPage, personId, personName, legislatorVoteOption);
    }

    public void pullRecordsForPage(int startPage)
    {
        mStartPage = startPage;

        if(mStartPage <= mMeta.Pages)
        {
            pullRecordStep(startPage, mPersonId, mPersonName, mVotingOption);
        }
    }

    private void pullRecordStep(final int page, final String personId, final String personName, final LegislatorVotingOption legislatorVoteOption) {
        if(page == 0)
            return;

        if(page > (mStartPage + mInterval - 1))
        {
            switchState(FINISHED);
            return;
        }

        if(mPagesPulled.contains(page))
        {
            pullRecordStep(page+1, personId, personName, legislatorVoteOption);
            return;
        }

        mVotingOption = legislatorVoteOption;
        mCurrentPage = page;
        this.setRouteParameters(getUrlParameters(null, personId, legislatorVoteOption));
        String votesUrl = getUrl(page, mUsePaging);
        Ion.with(mContext)
                .load(votesUrl)
                .noCache()
                .addHeader(mApiConfig.getApiKeyHeader(), mApiConfig.getApiKey())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            switchState(FINISHED);
                            return;
                        }

                        JsonArray data = result.getAsJsonArray("data");
                        if (data == null) {
                            switchState(FINISHED);
                            return;
                        }
                        JsonObject meta = result.getAsJsonObject("meta");
                        mMeta = mGson.fromJson(meta, Meta.class);

                        for (JsonElement element : data) {
                            Vote vote = mGson.fromJson(element, Vote.class);
                            vote.setmPersonId(personId);
                            vote.setmPersonName(personName);
                            vote.setmPersonVoteOption(legislatorVoteOption);
                            mVotes.put(personId + vote.getmBillId(), vote);
                        }

                        mPagesPulled.add(page);

                        if (mMeta.Page != mMeta.Pages && mMeta.Page > 0) {
                            mCurrentPage = mMeta.Page + 1;
                            pullRecordStep(mMeta.Page + 1, personId, personName, legislatorVoteOption);
                        }

                        switchState(FINISHED);
                        return;
                    }
                });



    }

    @Override
    public String getRoute() {
        return ROUTE;
    }

    public String getUrlParameters(String billId, String personId, LegislatorVotingOption legislatorVoteOption){
        String result = "";

        if(billId != null && billId.length() > 0)
        {
            result += "bill=" + billId;

        }
        if(personId != null && personId.length() > 0)
        {
            if(billId!=null && billId.length() > 0)
            {
                result += "&";
            }
            result += "voter=" + personId;

        }
        if(legislatorVoteOption == LegislatorVotingOption.YES)
        {
            result += "&option=yes";
        }
        if(legislatorVoteOption == LegislatorVotingOption.NO)
        {
            result += "&option=no";
        }
        if(legislatorVoteOption == LegislatorVotingOption.NOTVOTING)
        {
            result += "&option=not+voting";
        }

        return result;
    }
    @Override
    protected void switchState(@STATE int newState) {
        mState = newState;
        switch (mState) {
            case IDLE:
                break;
            case PULLING:
                break;
            case FINISHED:
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(mContext);
                Intent pullSuccessBroadcastIntent = new Intent(PULL_SUCCESS);
                broadcastManager.sendBroadcast(pullSuccessBroadcastIntent);
                break;
        }
    }

    public String getmPersonId() {
        return mPersonId;
    }

    public ArrayList<VoteItem> getVoteItems()
    {
        Vote[] votes = mVotes.values().toArray(new Vote[mVotes.values().size()]);
        Arrays.sort(votes);
        ArrayList<VoteItem> result = new ArrayList<>();
        int index = 1;
        for(Vote vt: votes)
        {
            VoteItem temp = new VoteItem();
            temp.Id = vt.getmId();
            temp.Result = vt.getmResult();
            temp.Index = index;
            temp.BillId = vt.getmBillId();
            temp.PersonId = vt.getmPersonId();
            temp.Name = vt.getmPersonName();
            temp.PersonVotingOption = vt.getmPersonVoteOption();
            temp.Updated = vt.getmUpdated();
            result.add(temp);
            index+=1;

        }

        switchState(DONE);

        return result;
    }
}
