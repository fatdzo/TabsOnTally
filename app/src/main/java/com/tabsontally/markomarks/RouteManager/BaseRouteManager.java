package com.tabsontally.markomarks.RouteManager;

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

    @IntDef({IDLE, PULLING, FINISHED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE { }
    public final static int IDLE = 0;
    public final static int PULLING = 1;
    public final static int FINISHED = 2;

    protected Context mContext;
    protected APIConfig mApiConfig;

    protected  @STATE int mState;

    protected int mCurrentPage = 1; //page starts at 1
    protected boolean mUsePaging = true;

    public BaseRouteManager(Context context, APIConfig config) {
        mContext = context;
        mApiConfig = config;
    }

    protected String getUrl(int page, boolean usePaging) {
        mUsePaging = usePaging;
        String routeParams = getRouteParameters();

        String result = mApiConfig.getUrl() + getRoute();

        if(routeParams.length() > 0 || usePaging)
        {
            result += "?";
        }

        if(routeParams.length() > 0)
        {
            result += routeParams;
            if(usePaging)
            {
                result += "&";
            }

        }

        if(usePaging)
        {
            result += "page=" + page;
        }

        return result;

    }

    public abstract String getRoute();

    public abstract String getRouteParameters();

    protected abstract void switchState(@STATE int newState);
}
