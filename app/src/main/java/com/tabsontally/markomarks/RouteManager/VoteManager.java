package com.tabsontally.markomarks.RouteManager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tabsontally.markomarks.json.VoteDeserializer;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.Vote;
import com.tabsontally.markomarks.tabsontally.VoteItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by MarkoPhillipMarkovic on 1/19/2016.
 */
public class VoteManager extends BaseRouteManager {

    public static final String PULL_SUCCESS = "com.tabsontally.markomarks.votemanager.PULL SUCCESS";
    private static final String ROUTE = "votes/";

    public boolean isFinished = false;

    private Gson mGson;

    HashMap<String, Vote> mVotes;

    public VoteManager(Context context, APIConfig config, Gson gson) {
        super(context, config);
        mVotes = new HashMap<>();
        mUsePaging = false;
        mUsedUrls = new HashMap<>();
        mGson = gson;
    }

    //legislatorVoteOption - it can be Yes, No,
    public void pullRecords(String billId, String personId, LegislatorVotingOption legislatorVoteOption)
    {
        switchState(IDLE);
        pullRecordStep(mCurrentPage, billId, personId, legislatorVoteOption);
    }

    private void pullRecordStep(int page, final String billId, final String personId, final LegislatorVotingOption legislatorVoteOption) {
        if(page == 0)
            return;
        //We are trying to make sure that we don't call the same URL more than once. Around 100 API calls are made when the results are loaded.
        if(!mUsedUrls.containsKey(billId + personId))
        {
            this.setRouteParameters(getUrlParameters(billId, personId, legislatorVoteOption));
            String votesUrl = getUrl(page, mUsePaging);
            mUsedUrls.put(billId + personId, votesUrl);
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

                            for (JsonElement element : data) {
                                Vote vote = mGson.fromJson(element, Vote.class);
                                vote.setmBillId(billId);
                                vote.setmPersonId(personId);
                                vote.setmPersonVoteOption(legislatorVoteOption);
                                mVotes.put(billId + personId, vote);
                            }

                            switchState(FINISHED);
                            return;
                        }
                    });
        }
        else
        {
            switchState(FINISHED);
            return;
        }



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
            temp.PersonVotingOption = vt.getmPersonVoteOption();
            temp.Updated = vt.getmUpdated();
            result.add(temp);
            index+=1;

        }

        return result;
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
                switchState(IDLE);
                break;
        }
    }
}
