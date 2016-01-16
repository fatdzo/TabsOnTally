package com.tabsontally.markomarks.json;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.tabsontally.markomarks.model.Bill;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by johnli on 1/16/16.
 */
public class BillDeserializer implements JsonDeserializer<Bill> {
    Gson gson = new Gson();

    @Override
    public Bill deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        /*
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gson.fromJson(json, type);
        String resultType = (String) data.get("type");
        String resultId = data.get("id").toString();
        Map<String, Object> attr = (Map<String, Object>) data.get("attributes");

        String name = attr.get("name").toString();
        String imageUrl = attr.get("image").toString();
        Map<String,String> extra = (Map<String, String>) attr.get("extras");
        Person person = new Person(resultId, resultType, name, "", "", imageUrl, "", "", "", "" , "", "", null, extra);
        return person;
         */
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gson.fromJson(json, type);
        String resultType = (String) data.get("type");
        String resultId = data.get("id").toString();
        Map<String, Object> attr = (Map<String, Object>) data.get("attributes");

        String title = attr.get("title").toString();
        String identifier = attr.get("identifier").toString();
        String[] subjects = getStringArray(attr.get("subject").toString());
        String[] classification = getStringArray(attr.get("classification").toString());

        Bill bill = new Bill(resultId, resultType, identifier, title, "", "", classification, subjects);
        return bill;
    }

    String[] getStringArray(@NonNull String input) {
        try {
            return gson.fromJson(input, String[].class);
        }catch (Exception e) {
            //HACK
            return new String[]{input.substring(1, input.length()-1)};  //removes '[' and ']'
        }

    }
}
/*
                "legislative_session": {
                    "identifier": "2015",
                    "name": "2015 Regular Session"
                },
                "created_at": "2015-10-28T03:21:47.683513Z",
                "updated_at": "2015-10-28T03:21:47.683535Z",
                "extras": {
                    "places": []
                },
                "identifier": "SB 228",
                "title": "Online Voter Application",
                "classification": [
                    "bill"
                ],
                "subject": [
                    "appropriations",
                    "taxation"
                ]
 */
