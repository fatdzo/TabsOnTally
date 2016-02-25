package com.tabsontally.markomarks.model.json;

import java.io.Serializable;

public class ContactDetail implements Serializable {
    String mLabel;
    String mType;
    String mValue;
    String mNote;

    public String getmValue() {
        return mValue;
    }

    public String getmLabel() {
        return mLabel;
    }

    public String getmType() {
        return mType;
    }

    public String getmNote() {
        return mNote;
    }

    public ContactDetail(String label, String type, String value, String note) {
        mLabel = label;
        mType = type;
        mValue = value;
        mNote = note;
    }
}
