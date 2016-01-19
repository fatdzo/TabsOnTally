package com.tabsontally.markomarks.json;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.tabsontally.markomarks.model.Meta;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by MarkoPhillipMarkovic on 1/19/2016.
 */
public class MetaDeserializer implements JsonDeserializer<Meta> {
    @Override
    public Meta deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gson.fromJson(json, type);
        Map<String, Object> pagination = (Map<String, Object>) data.get("pagination");

        Double page = (Double)pagination.get("page");
        Double pages = (Double)pagination.get("pages");
        Double count = (Double)pagination.get("count");

        Meta result = new Meta(page.intValue(), pages.intValue(), count.intValue());
        return result;
    }
}