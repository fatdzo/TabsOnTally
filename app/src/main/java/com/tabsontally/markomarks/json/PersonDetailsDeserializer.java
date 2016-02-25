package com.tabsontally.markomarks.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tabsontally.markomarks.model.json.PersonDetails;
import com.tabsontally.markomarks.model.minor.ContactDetail;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MarkoPhillipMarkovic on 2/11/2016.
 */
public class PersonDetailsDeserializer implements JsonDeserializer<PersonDetails> {
    @Override
    public PersonDetails deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        String resultType = jObject.get("type").getAsString();
        String resultId = jObject.get("id").getAsString();

        JsonObject attrObject = jObject.get("attributes").getAsJsonObject();
        JsonArray jsonContactArray = attrObject.get("contact_details").getAsJsonArray();

        ArrayList<ContactDetail> contactDetails = new ArrayList<>();

        for (int i = 0; i < jsonContactArray.size(); i++) {
            JsonObject el = jsonContactArray.get(i).getAsJsonObject();
            String elNote = el.get("note").getAsString();
            String elValue = el.get("value").getAsString();
            String elType = el.get("type").getAsString();
            String elLabel = el.get("label").getAsString();

            ContactDetail temp = new ContactDetail(elLabel,elType,elValue,elNote);
            contactDetails.add(temp);
        }


        String name = attrObject.get("name").getAsString();
        String sortName = attrObject.get("sort_name").getAsString();
        String familyName = attrObject.get("family_name").getAsString();
        String givenName = attrObject.get("given_name").getAsString();
        String imageUrl = attrObject.get("image").getAsString();
        String gender = attrObject.get("gender").getAsString();
        String summary = attrObject.get("summary").getAsString();
        String nationalIdentity = attrObject.get("national_identity").getAsString();
        String biography = attrObject.get("biography").getAsString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

        String birthDateString = attrObject.get("birth_date").getAsString();
        String deathDateString = attrObject.get("death_date").getAsString();

        Date birthDate = new Date();
        try
        {
            birthDate = formatter.parse(birthDateString);
        }
        catch (ParseException e){

        }

        Date deathDate = new Date();
        try
        {
            deathDate = formatter.parse(deathDateString);
        }
        catch (ParseException e){

        }

        PersonDetails personDetails = new PersonDetails(resultId,
                resultType,
                name,
                sortName,
                familyName,
                givenName,
                gender,
                summary,
                nationalIdentity,
                biography,
                birthDate,
                deathDate,
                contactDetails,
                imageUrl );

        return personDetails;
    }

}
