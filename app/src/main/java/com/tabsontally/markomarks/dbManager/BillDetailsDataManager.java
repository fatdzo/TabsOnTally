package com.tabsontally.markomarks.dbManager;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tabsontally.markomarks.model.json.BillDetail;
import com.tabsontally.markomarks.model.db.BillDetailDB;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 2/18/2016.
 */
public class BillDetailsDataManager {
    private ArrayList<BillDetailDB> mBillDetails;

    private static final String USERFILECONTENTNAME = "tabsontally_billdetails_content.txt";
    private Gson mGson;
    Type arrayListType;

    public BillDetailsDataManager()
    {
        mBillDetails = new ArrayList<>();
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        arrayListType  = new TypeToken<ArrayList<BillDetailDB>>() {}.getType();
        mGson = gson;
    }

    public BillDetailDB convertToBillDetailDB(BillDetail source)
    {
        BillDetailDB target = new BillDetailDB(source.getId(), source.getType(), source.getTitle(), source.getSubjects());
        return target;
    }

    public void appendBillDetailsToFile(Context ctx, BillDetail detail)
    {
        // TODO Auto-generated method stub
        try {
            BillDetailDB convertedBillDetail = convertToBillDetailDB(detail);
            mBillDetails.add(convertedBillDetail);
            String result = new Gson().toJson(mBillDetails.clone());
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

    public void addBillDetailToContent(final Context ctx, final BillDetail newDetail)
    {
        if(!mBillDetails.contains(newDetail))
        {
            appendBillDetailsToFile(ctx, newDetail);
        }
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
            ArrayList<BillDetailDB> bdList = mGson.fromJson(data, arrayListType);
            mBillDetails.addAll(bdList);
        }
    }

    public BillDetailDB findDetailDbById(String billId)
    {
        for(BillDetailDB bd: mBillDetails)
        {
            if(bd.getId().equals(billId))
            {
                return bd;
            }
        }
        return null;
    }
}
