package com.tabsontally.markomarks.model.json;

/**
 * Created by MarkoPhillipMarkovic on 2/15/2016.
 */
public class LinkObject {

    public String mText;
    public String mUrl;
    public String mMediaType;

    public LinkObject(String text, String url, String mediaType)
    {
        mText = text;
        mUrl = url;
        mMediaType = mediaType;
    }

    public String getMediaType() {
        return mMediaType;
    }

    public String getText() {
        return mText;
    }

    public String getUrl() {
        return mUrl;
    }



}
