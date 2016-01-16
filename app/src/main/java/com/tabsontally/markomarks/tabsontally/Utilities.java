package com.tabsontally.markomarks.tabsontally;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class Utilities {

    public String BaseBillsQueryURL;
    public String BasePersonQueryURL;
    public String BaseVoteURL;
    public String BaseURL = "https://tabsontallahassee.com/api/";

    public Utilities()
    {
        BaseBillsQueryURL = BaseURL +  "bills/";
        BasePersonQueryURL = BaseURL + "people/";
        BaseVoteURL = BaseURL + " votes/";
    }


    public String getLegislatorsInYourAreaUrl(long latitude, long longitude)
    {
        String result = "";

        result += BasePersonQueryURL + "?" + "latitude=" + String.valueOf(Math.round(latitude * 100.0) / 100.0) + "&" + "longitude=" + String.valueOf(Math.round(longitude * 100.0) / 100.0);

        return result;
    }

    public String getBillsUrl()
    {
        String result ="";
        result += BaseBillsQueryURL;
        return result;
    }

    public String getVoteUrl(String billId, String personId)
    {
        String result = "";
        result += BaseVoteURL + "?" + "voter=" + personId + "&" + "bill=" + billId;

        return result;
    }

    public String GetJSON(String urlz)
    {

      return "";
    }





}
