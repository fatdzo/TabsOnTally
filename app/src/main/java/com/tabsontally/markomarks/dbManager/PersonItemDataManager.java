package com.tabsontally.markomarks.dbManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tabsontally.markomarks.model.json.PersonDetails;
import com.tabsontally.markomarks.model.items.PersonItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 2/20/2016.
 */
public class PersonItemDataManager {
    public ArrayList<PersonItem> getPersonItems() {
        return mPersonItems;
    }

    private ArrayList<PersonItem> mPersonItems;

    private static final String USERFILECONTENTNAME = "tabsontally_person_details_content.txt";
    private Gson mGson;
    Type arrayListType;

    public PersonItemDataManager()
    {
        mPersonItems = new ArrayList<>();
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        arrayListType  = new TypeToken<ArrayList<PersonItem>>() {}.getType();
        mGson = gson;
    }

    public void appendPersonDetailsToPersonAndFile(Context ctx, PersonDetails personDetails)
    {
        try {

            for(PersonItem p: mPersonItems)
            {
                if(p.Id.equals(personDetails.getId()))
                {
                    p.Details = personDetails;
                }
            }

            String result = new Gson().toJson(mPersonItems.clone());
            FileOutputStream fos;
            fos = ctx.openFileOutput(USERFILECONTENTNAME, Context.MODE_PRIVATE);
            fos.write(result.getBytes());
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addPersonDetailToContent(final Context ctx, final PersonDetails newDetail)
    {
        appendPersonDetailsToPersonAndFile(ctx, newDetail);
    }
    public void loadUserFileData(Context ctx)
    {
        //Loading user data from content file
        String result = null;
        try{
            StringBuilder sb = new StringBuilder();
            FileInputStream fis;
            BufferedReader reader;
            fis = ctx.openFileInput(USERFILECONTENTNAME);
            reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            if(sb.length()>0)
            {
                result = sb.toString();
            }

            if(result!=null && result.length() > 0){
                loadFromUserContentData(result);
            }

            reader.close();
            fis.close();

        } catch(OutOfMemoryError om){
            om.printStackTrace();
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public void loadFromUserContentData(String data)
    {
        if(data.length() > 0)
        {
            ArrayList<PersonItem> bdList = mGson.fromJson(data, arrayListType);
            Log.e("TABSONTALLY", "PERSONLIST LOADED =>" + String.valueOf(bdList.size()));
            mPersonItems.addAll(bdList);
        }
    }
}
