package com.tabsontally.markomarks.routemanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tabsontally.markomarks.json.BillDeserializer;
import com.tabsontally.markomarks.json.MetaDeserializer;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.json.Bill;
import com.tabsontally.markomarks.model.json.Meta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnli on 1/16/16.
 */
public class BillManager  extends BaseRouteManager {
    public static final String PULL_SUCCESS = "com.jli.tabsapiexample.billmanager.PULL SUCCESS";
    public static final int PAGE_SIZE = 50;

    Meta mMeta = new Meta(1,1,0);

    Map<String, Bill> mBills;
    private static final String ROUTE = "bills/";
    private final Gson mGson;

    private Context ctx;

    public BillManager(Context context, APIConfig config) {
        super(context, config);

        ctx = context;

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Bill.class, new BillDeserializer());
        gsonBuilder.registerTypeAdapter(Meta.class, new MetaDeserializer());
        final Gson gson = gsonBuilder.create();

        mGson = gson;

        mBills = new HashMap<>();
        switchState(IDLE);
        //pullRecords(mCurrentPage);
    }

    /*public ArrayList<BillItem> getBillItemList()
    {
        ArrayList<BillItem> result = new ArrayList<>();

        Bill[] billList = mBills.values().toArray(new Bill[mBills.values().size()]);

        Arrays.sort(billList);

        int index = 1;
        for(Bill bll: billList)
        {
            BillItem b = new BillItem();
            b.Title = bll.getTitle().trim();
            b.Id = bll.getId();
            b.Index = (mCurrentPage - 1) * PAGE_SIZE + index;
            b.Description = "";
            b.CreatedAt = bll.getmCreated();
            b.UpdatedAt = bll.getmUpdated();
            result.add(b);
            index++;
        }

        return result;
    }*/


    @Override
    public String getRoute() {
        return ROUTE;
    }

    protected void pullRecords(int page){

        pullRecordStep(page);

    }

    protected void pullAllRecords() {
        if(mState != IDLE){
            return;
        }
        mState = PULLING;
        mCurrentPage = 1;
        pullRecordStep(mCurrentPage++);
    }

    public void pullRecordPage(int page){
        mBills.clear();
        switchState(FINISHED);
        mCurrentPage = page;
        pullRecordStep(page);
    }

    private void pullRecordStep(int page) {
        if(page == 0)
            return;

        Ion.with(mContext)
                .load(getUrl(page, mUsePaging))
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
                            Bill bill = mGson.fromJson(element, Bill.class);
                            mBills.put(bill.getId(), bill);
                        }

                        //if (mMeta.Page != mMeta.Pages) {
                        //    pullRecordStep(mMeta.Page + 1);
                        //}

                        switchState(FINISHED);
                        return;
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

    public Meta getmMeta()
    {
        return mMeta;
    }


}
