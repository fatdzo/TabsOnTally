package com.tabsontally.markomarks.json;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.tabsontally.markomarks.model.json.Person;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class PersonDeserializer implements JsonDeserializer<Person> {
    @Override
    public Person deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gson.fromJson(json, type);
        String resultType = (String) data.get("type");
        String resultId = data.get("id").toString();
        Map<String, Object> attr = (Map<String, Object>) data.get("attributes");

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
        Date birthDate = new Date();
        try
        {
            birthDate = formatter.parse(birthDateString);
        }
        catch (ParseException e){

        }

        String deathDateString = attr.get("death_date").toString();
        Date deathDate = new Date();
        try
        {
            deathDate = formatter.parse(deathDateString);
        }
        catch (ParseException e){

        }

        Map<String,String> extra = (Map<String, String>) attr.get("extras");
        Person person = new Person(resultId, resultType, name,  sortName,givenName, imageUrl, gender, summary, nationalIdentity, biography, birthDate,deathDate, extra);
        return person;
    }
}