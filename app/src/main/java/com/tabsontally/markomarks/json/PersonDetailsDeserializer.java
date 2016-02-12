package com.tabsontally.markomarks.json;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.tabsontally.markomarks.model.PersonDetails;
import com.tabsontally.markomarks.model.minor.ContactDetail;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by MarkoPhillipMarkovic on 2/11/2016.
 */
public class PersonDetailsDeserializer implements JsonDeserializer<PersonDetails> {
    @Override
    public PersonDetails deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gson.fromJson(json, type);
        String resultType = (String) data.get("type");
        String resultId = (String) data.get("id");
        Map<String, Object> attr = (Map<String, Object>) data.get("attributes");

        JsonObject jObject = json.getAsJsonObject();
        JsonObject dataObj = jObject.get("attributes").getAsJsonObject();

        JsonArray jsonContactArray = dataObj.get("contact_details").getAsJsonArray();

        ArrayList<ContactDetail> contactDetails = new ArrayList<>();

        for (int i = 0; i < jsonContactArray.size(); i++) {
            JsonObject el = jsonContactArray.get(i).getAsJsonObject();
            String elNote = el.get("note").toString();
            String elValue = el.get("value").toString();
            String elType = el.get("type").toString();
            String elLabel = el.get("label").toString();

            ContactDetail temp = new ContactDetail(elLabel,elType,elValue,elNote);
            contactDetails.add(temp);
        }


        String name = attr.get("name").toString();
        String imageUrl = attr.get("image").toString();
        String sortName = attr.get("sort_name").toString();
        String familyName = attr.get("family_name").toString();
        String givenName = attr.get("given_name").toString();
        String gender = attr.get("gender").toString();
        String summary = attr.get("summary").toString();
        String nationalIdentity = attr.get("national_identity").toString();
        String biography = attr.get("biography").toString();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

        String birthDateString = attr.get("birth_date").toString();


        PersonDetails personDetails = new PersonDetails(resultId, resultType, contactDetails, imageUrl);

        return personDetails;
    }

}
