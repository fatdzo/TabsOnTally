package com.tabsontally.markomarks.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tabsontally.markomarks.model.json.BillDetail;
import com.tabsontally.markomarks.model.json.DocumentObject;
import com.tabsontally.markomarks.model.json.VersionObject;
import com.tabsontally.markomarks.model.minor.LinkObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MarkoPhillipMarkovic on 2/9/2016.
 */
public class BillDetailDeserializer  implements JsonDeserializer<BillDetail> {
    @Override
    public BillDetail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        String resultType = jObject.get("type").getAsString();
        String resultId = jObject.get("id").getAsString();

        JsonObject attrObject = jObject.get("attributes").getAsJsonObject();

        String resultTitle = attrObject.get("title").getAsString();
        String resultIdentifier = attrObject.get("identifier").getAsString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

        String updatedString = attrObject.get("updated_at").getAsString();
        Date updatedAtDate = null;
        try {
            updatedAtDate = formatter.parse(updatedString);
        } catch (ParseException e) {

        }

        String createdString = attrObject.get("created_at").getAsString();
        Date createdAtDate = null;
        try {
            createdAtDate = formatter.parse(createdString);
        } catch (ParseException e) {

        }


        JsonArray documentList = attrObject.get("documents").getAsJsonArray();
        ArrayList<DocumentObject> resultDocuments = new ArrayList<>();
        for (int i = 0; i < documentList.size(); i++) {
            JsonObject el = documentList.get(i).getAsJsonObject();
            String elNote = el.get("note").getAsString();
            String dateString = el.get("date").getAsString();

            Date dateValue = new Date();
            try{
                dateValue = formatter.parse(dateString);
            }
            catch (ParseException e) {
            }

            ArrayList<LinkObject> documentLinks = new ArrayList<>();
            JsonArray docLinks = el.get("links").getAsJsonArray();

            for(int j=0; j< docLinks.size(); j++)
            {
                JsonObject lnk = docLinks.get(j).getAsJsonObject();

                String lnkText = lnk.get("text").getAsString();
                String lnkMediaType = lnk.get("media_type").getAsString();
                String lnkUrl = lnk.get("url").getAsString();

                LinkObject temp = new LinkObject(lnkText,lnkUrl,lnkMediaType);
                documentLinks.add(temp);
            }

            DocumentObject temp = new DocumentObject(elNote, dateValue, documentLinks );
            resultDocuments.add(temp);

        }


        JsonArray versionList = attrObject.get("versions").getAsJsonArray();
        ArrayList<VersionObject> resultVersions = new ArrayList<>();
        for (int i = 0; i < versionList.size(); i++) {
            JsonObject ver = versionList.get(i).getAsJsonObject();
            String elNote = ver.get("note").getAsString();
            String dateString = ver.get("date").getAsString();

            Date dateValue = new Date();
            try{
                dateValue = formatter.parse(dateString);
            }
            catch (ParseException e) {
            }

            ArrayList<LinkObject> versionLinks = new ArrayList<>();
            JsonArray verLinks = ver.get("links").getAsJsonArray();

            for(int j=0; j< verLinks.size(); j++)
            {
                JsonObject lnk = verLinks.get(j).getAsJsonObject();

                String lnkText = lnk.get("text").getAsString();
                String lnkMediaType = lnk.get("media_type").getAsString();
                String lnkUrl = lnk.get("url").getAsString();

                LinkObject temp = new LinkObject(lnkText,lnkUrl,lnkMediaType);
                versionLinks.add(temp);
            }

            VersionObject temp = new VersionObject(elNote, dateValue, versionLinks );
            resultVersions.add(temp);

        }

        JsonArray subjectList = attrObject.get("subject").getAsJsonArray();
        ArrayList<String> subjects = new ArrayList<>();
        for (int k = 0; k < subjectList.size(); k++) {
            String sub = subjectList.get(k).getAsString();
            subjects.add(sub);
        }

        BillDetail result = new BillDetail(
                resultId,
                resultType,
                resultTitle,
                createdAtDate,
                updatedAtDate,
                resultIdentifier,
                subjects,
                resultDocuments,
                resultVersions);

        return result;
    }
}