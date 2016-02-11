package com.tabsontally.markomarks.json;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.tabsontally.markomarks.model.BillDetail;
import com.tabsontally.markomarks.model.DocumentObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by MarkoPhillipMarkovic on 2/9/2016.
 */
public class BillDetailDeserializer  implements JsonDeserializer<BillDetail> {
    @Override
    public BillDetail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> data = gson.fromJson(json, type);
        String resultId = data.get("id").toString();
        String resultType = data.get("type").toString();

        Map<String, Object> attr = (Map<String, Object>) data.get("attributes");

        String resultTitle = attr.get("title").toString();
        String resultIdentifier = attr.get("identifier").toString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

        String updatedString = attr.get("updated_at").toString();
        Date updatedAtDate = new Date();
        try {
            updatedAtDate = formatter.parse(updatedString);
        } catch (ParseException e) {

        }

        /*Map<String, Object> realtionships = (Map<String, Object>) data.get("relationships");

        Map<String, Object> billRelationship = (Map<String, Object>) realtionships.get("bill");
        Map<String, Object> billRelationshipData = (Map<String, Object>) billRelationship.get("data");
        String billId = billRelationshipData.get("id").toString();
        String billType = billRelationshipData.get("type").toString();

        Map<String, Object> organizationRelationship = (Map<String, Object>) realtionships.get("organization");
        Map<String, Object> organizationRelationshipData = (Map<String, Object>) organizationRelationship.get("data");
        String organizationId = organizationRelationshipData.get("id").toString();
        String organizationType = organizationRelationshipData.get("type").toString();


        VoteRelationship relationship = new VoteRelationship(organizationId, organizationType, billId, billType);
         */

        BillDetail result = new BillDetail(resultId,resultType, resultTitle, updatedAtDate, resultIdentifier, new ArrayList<String>(), new ArrayList<DocumentObject>());

        return result;
    }
}