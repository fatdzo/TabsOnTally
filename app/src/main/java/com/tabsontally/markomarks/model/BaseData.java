package com.tabsontally.markomarks.model;

import android.support.annotation.NonNull;


public class BaseData {
    private String mId;
    private String mType;

    public BaseData(@NonNull String id, @NonNull String type) {
        mId = id;
        mType = type;
    }

    public String getType() { return mType; }
    public String getId() { return mId; }
}
