package com.tabsontally.markomarks.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.tabsontally.markomarks.model.Person;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

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
        Map<String,String> extra = (Map<String, String>) attr.get("extras");
        Person person = new Person(resultId, resultType, name, "", "", imageUrl, "", "", "", "" , "", "", extra);
        return person;
    }
}