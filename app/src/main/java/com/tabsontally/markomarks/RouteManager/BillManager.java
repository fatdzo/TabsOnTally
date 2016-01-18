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
import com.tabsontally.markomarks.RouteManager.BaseRouteManager;
import com.tabsontally.markomarks.json.BillDeserializer;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.Bill;
import com.tabsontally.markomarks.tabsontally.BillItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnli on 1/16/16.
 */
public class BillManager  extends BaseRouteManager {
    public static final String PULL_SUCCESS = "com.jli.tabsapiexample.billmanager.PULL SUCCESS";

    Map<String, Bill> mBills;
    private static final String ROUTE = "bills/";

    public BillManager(Context context, APIConfig config) {
        super(context, config);
        mBills = new HashMap<>();
        switchState(IDLE);
        pullAllRecords();
    }

    public ArrayList<Bill> BillList;

    public ArrayList<BillItem> getBillItemList()
    {
        ArrayList<BillItem> result = new ArrayList<>();

        Bill[] billList = mBills.values().toArray(new Bill[mBills.values().size()]);
        Arrays.sort(billList);
        int index = 1;
        for(Bill bll: billList)
        {
            BillItem b = new BillItem();
            b.Title = bll.getmTitle().trim();
            b.Id = bll.getId();
            b.Index = index;
            b.Description = "";
            result.add(b);
            index++;
        }
        return result;
    }


    @Override
    public String getRoute() {
        return ROUTE;
    }

    protected void pullAllRecords() {
        if(mState != IDLE){
            return;
        }
        mState = PULLING;
        mCurrentPage = 1;
        pullRecordStep(mCurrentPage++);
        Log.d("Number of bills pulled", String.valueOf(mBills.size()));
    }

    private void pullRecordStep(int page) {
        if(page == 0)
            return;

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Bill.class, new BillDeserializer());
        final Gson gson = gsonBuilder.create();

        Ion.with(mContext)
                .load(getUrl(page))
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
                            Bill bill = gson.fromJson(element, Bill.class);
                            mBills.put(bill.getId(), bill);
                        }
                        if (mCurrentPage > 3) {
                            switchState(FINISHED);
                            return;
                        }
                        pullRecordStep(mCurrentPage++);
                    }
                });
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

    public Bill getBillWithId(String id) {
        return mBills.get(id);
    }
    public int getCount() { return mBills.size(); }

}
