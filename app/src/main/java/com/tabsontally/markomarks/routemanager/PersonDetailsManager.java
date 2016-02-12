package com.tabsontally.markomarks.routemanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tabsontally.markomarks.json.PersonDetailsDeserializer;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.PersonDetails;
import com.tabsontally.markomarks.model.minor.ContactDetail;

/**
 * Created by MarkoPhillipMarkovic on 2/11/2016.
 */
public class PersonDetailsManager extends BaseRouteManager {

    public static final String PULL_SUCCESS = "com.tabsontally.markomarks.personDetailManager.PULL SUCCESS";

    private Gson mGson;

    String mPersonId;

    public PersonDetails getPerson() {
        return person;
    }

    PersonDetails person;

    public PersonDetailsManager(Context context, APIConfig config, String personId) {
        super(context, config);
        mUsePaging = false;

        mPersonId = personId;

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PersonDetails.class, new PersonDetailsDeserializer());
        final Gson gson = gsonBuilder.create();
        mGson = gson;
        switchState(IDLE);
    }

    public String getmPersonId() {
        return mPersonId;
    }
    @Override
    public String getRoute() {
        return mPersonId + "/";
    }

    public void pullRecords(){

        switchState(PULLING);
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

                        person = mGson.fromJson(result.getAsJsonObject("data"), PersonDetails.class);

                        if(person == null) {
                            switchState(FINISHED);
                            return;
                        }

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
}
