package com.tabsontally.markomarks.routemanager;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.Log;


import com.tabsontally.markomarks.model.APIConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by johnli on 1/16/16.
 */
public abstract class BaseRouteManager {

    @IntDef({IDLE, PULLING, FINISHED, DONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE { }
    public final static int IDLE = 0;
    public final static int PULLING = 1;
    public final static int FINISHED = 2;
    public final static int DONE = 3;

    protected Context mContext;
    protected APIConfig mApiConfig;

    protected  @STATE int mState;

    protected int mCurrentPage = 1; //page starts at 1
    protected int mStartPage = 1;
    protected int mInterval = 3;

    protected boolean mUsePaging = true;

    private String mRouteParameters = "";

    public BaseRouteManager(Context context, APIConfig config) {
        mContext = context;
        mApiConfig = config;
    }

    public boolean isManagerDone()
    {
        return mState == DONE;
    }

    protected String getUrl(int page, boolean usePaging) {
        mUsePaging = usePaging;

        String result = mApiConfig.getUrl() + getRoute();

        if(mRouteParameters.length() > 0 || usePaging)
        {
            result += "?";
        }

        if(mRouteParameters.length() > 0)
        {
            result += mRouteParameters;
            if(usePaging)
            {
                result += "&";
            }
        }

        if(usePaging)
        {
            result += "page=" + page;
        }

        Log.e("TABSONTALLY", result);

        return result;

    }

    public abstract String getRoute();

    /*The string should look something like "bill=12345&voter=12345" the getUrl() will handle the rest. Every derived class handles their own set of parameters so it seemed appropriate*/
    protected void setRouteParameters(String parameters)
    {
        mRouteParameters = parameters;
    }

    protected abstract void switchState(@STATE int newState);
}
