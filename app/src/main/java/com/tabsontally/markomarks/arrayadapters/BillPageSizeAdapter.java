package com.tabsontally.markomarks.arrayadapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.tabsontally.markomarks.model.items.PageSizeItem;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 2/7/2016.
 */
public class BillPageSizeAdapter extends ArrayAdapter<PageSizeItem> {

    private Context ctx;

    public BillPageSizeAdapter(Context context, ArrayList<PageSizeItem> items)
    {
        super(context, 0, items);
        ctx = context;
    }
}
