package com.tabsontally.markomarks.model.db;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 2/19/2016.
 */
public class BillDetailDB implements Serializable{
    private String Id;
    private String Title;
    private String Type;
    private ArrayList<String> Subjects;

    public String getTitle() {
        return Title;
    }

    public ArrayList<String> getSubjects() {
        return Subjects;
    }

    public String getId() {
        return Id;
    }

    public String getType() {
        return Type;
    }

    public BillDetailDB(String id, String type, String title, ArrayList<String> subjects)
    {
        Id = id;
        Title = title;
        Type = type;
        Subjects = subjects;
    }

}
