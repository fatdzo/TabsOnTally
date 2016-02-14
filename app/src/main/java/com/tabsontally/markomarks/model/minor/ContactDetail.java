package com.tabsontally.markomarks.model.minor;

import java.io.Serializable;

public class ContactDetail implements Serializable {
    String mLabel;
    String mType;

    String mValue;
    String mNote;

    public String getmValue() {
        return mValue.replace("\\n", "\n").replace("\"", "");
    }

    public String getmLabel() {
        return mLabel;
    }

    public String getmType() {
        return mType.replace("\"", "");
    }

    public String getmNote() {
        return mNote.replace("\"", "");
    }

    public ContactDetail(String label, String type, String value, String note) {
        mLabel = label;
        mType = type;
        mValue = value;
        mNote = note;
    }
}
