package com.tabsontally.markomarks.RouteManager;

import android.content.Context;
import android.support.annotation.IntDef;


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

    public BaseRouteManager(Context context, APIConfig config) {
        mContext = context;
        mApiConfig = config;
    }

    protected String getUrl(int page) {
        return (mApiConfig.getUrl() + getRoute() + "?page=" + page);
    }

    public abstract String getRoute();

    protected abstract void switchState(@STATE int newState);
}
