package com.tabsontally.markomarks.routemanager;

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
import com.tabsontally.markomarks.json.BillDetailDeserializer;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.BillDetail;

/**
 * Created by MarkoPhillipMarkovic on 2/9/2016.
 */
public class BillDetailManager extends BaseRouteManager {

    public static final String PULL_SUCCESS = "com.tabsontally.markomarks.billdetailmanager.PULL SUCCESS";
    private static final String ROUTE = "ocd-bill/";
    private Gson mGson;

    private BillDetail billDetail;

    private String mBillId;

    public BillDetail getBillDetail() {
        return billDetail;
    }

    public BillDetailManager(Context context, APIConfig config) {
        super(context, config);
        mUsePaging = false;

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BillDetail.class, new BillDetailDeserializer());
        final Gson gson = gsonBuilder.create();
        mGson = gson;
    }


    public void setmBillId(String mBillId) {
        this.mBillId = mBillId;
    }

    public String getRouteParameters(){
        String result = "";

        /*if(mLatitude != 0 && mLongitude != 0)
        {
            result += "latitude=" + String.valueOf(Math.round(mLatitude * 100.0) / 100.0) + "&longitude=" + String.valueOf(Math.round(mLongitude * 100.0) / 100.0);
        }*/
        return result;
    }

    public void pullRecords()
    {
        switchState(IDLE);
        pullRecordStep(1);
    }


    private void pullRecordStep(int page) {
        if(page == 0)
            return;

        //this.setRouteParameters(getRouteParameters());
        Ion.with(mContext)
                .load(getUrl(page, mUsePaging))
                .noCache()
                .addHeader(mApiConfig.getApiKeyHeader(), mApiConfig.getApiKey())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null) {
                            switchState(FINISHED);
                            return;
                        }

                        billDetail = mGson.fromJson(result.getAsJsonObject("data"), BillDetail.class);

                        Log.e("TABSONTALLY", "BILL DETAIL ->" + billDetail.getmTitle());

                        if(billDetail == null) {
                            switchState(FINISHED);
                            return;
                        }

                        switchState(FINISHED);
                        return;
                    }
                });
    }


    @Override
    public String getRoute() {
        return mBillId + "/";
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
