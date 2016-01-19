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
import com.tabsontally.markomarks.json.PersonDeserializer;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.Person;
import com.tabsontally.markomarks.tabsontally.PersonItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PeopleManager extends BaseRouteManager {
    public static final String PULL_SUCCESS = "com.jli.tabsapiexample.peoplemanager.PULL SUCCESS";

    Map<String, Person> mPeople;

    private double mLatitude = 0;
    private double mLongitude = 0;

    private static final String ROUTE = "people/";


    public PeopleManager(Context context, APIConfig config, double latitude, double longitude) {
        super(context, config);
        mPeople = new HashMap<>();
        switchState(IDLE);
        mLatitude = latitude;
        mLongitude = longitude;
        //pullAllRecords();
        mUsePaging = false;
        pullRecordStep(mCurrentPage);
    }

    public void setLatitudeLongitude(double latitude, double longitude)
    {
        mLatitude = latitude;
        mLongitude = longitude;
    }



    @Override
    public String getRoute() {

        return ROUTE;
    }

    @Override
    public String getRouteParameters(){
        String result = "";

        if(mLatitude != 0 && mLongitude != 0)
        {
            result += "latitude=" + String.valueOf(Math.round(mLatitude * 100.0) / 100.0) + "&longitude=" + String.valueOf(Math.round(mLongitude * 100.0) / 100.0);
        }
        return result;
    }

    public ArrayList<PersonItem> getPersonItemList()
    {
        Person[] persons = mPeople.values().toArray(new Person[mPeople.values().size()]);
        ArrayList<PersonItem> result = new ArrayList<>();
        int index = 1;
        for(Person per: persons)
        {
            PersonItem temp = new PersonItem();
            temp.FullName = per.getmName();
            temp.Id = per.getId();
            temp.Index = index;
            result.add(temp);
            index+=1;

        }
        return result;
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
        pullRecordStep(mCurrentPage);
    }

    private void pullRecordStep(int page) {
        if(page == 0)
            return;

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Person.class, new PersonDeserializer());
        final Gson gson = gsonBuilder.create();

        Ion.with(mContext)
                .load(getUrl(page, mUsePaging))
                .addHeader(mApiConfig.getApiKeyHeader(), mApiConfig.getApiKey())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null) {
                            switchState(FINISHED);
                            return;
                        }

                        JsonArray data = result.getAsJsonArray("data");
                        if(data == null) {
                            switchState(FINISHED);
                            return;
                        }

                        for (JsonElement element : data) {
                            Person person = gson.fromJson(element, Person.class);
                            mPeople.put(person.getId(), person);
                            Log.e("PERSONFOUND", person.getmName());
                        }
                        //pullRecordStep(mCurrentPage++);
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

    public Person getPersonWithId(String id) {
        return mPeople.get(id);
    }
    public int getCount() { return mPeople.size(); }
}
