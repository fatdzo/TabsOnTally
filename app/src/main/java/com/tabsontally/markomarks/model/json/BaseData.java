package com.tabsontally.markomarks.model.json;

import android.support.annotation.NonNull;

import java.io.Serializable;


public class BaseData implements Serializable{
    private String mId;
    private String mType;

    public BaseData(@NonNull String id, @NonNull String type) {
        mId = id;
        mType = type;
    }

    public String getType() { return mType; }
    public String getId() { return mId; }
}
