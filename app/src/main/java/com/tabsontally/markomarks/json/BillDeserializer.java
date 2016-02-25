package com.tabsontally.markomarks.json;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.tabsontally.markomarks.model.json.Bill;


import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by johnli on 1/16/16.
 */
public class BillDeserializer implements JsonDeserializer<Bill> {
    Gson gson = new Gson();

    @Override
    public Bill deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gson.fromJson(json, type);
        String resultType = (String) data.get("type");
        String resultId = data.get("id").toString();
        Map<String, Object> attr = (Map<String, Object>) data.get("attributes");

        String title = attr.get("title").toString();
        String identifier = attr.get("identifier").toString();
        String[] subjects = getStringArray(attr.get("subject").toString());
        String[] classification = getStringArray(attr.get("classification").toString());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

        String createdAt = attr.get("created_at").toString();
        Date createdAtDate = new Date();
        try
        {
            createdAtDate = formatter.parse(createdAt);
        }
        catch (ParseException e){

        }

        String updated_at = attr.get("updated_at").toString();
        Date updatedAtDate = new Date();
        try
        {
            updatedAtDate = formatter.parse(updated_at);
        }
        catch (ParseException e){

        }

        Bill bill = new Bill(resultId, resultType, identifier, title, "", "", classification, subjects,createdAtDate, updatedAtDate);
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
