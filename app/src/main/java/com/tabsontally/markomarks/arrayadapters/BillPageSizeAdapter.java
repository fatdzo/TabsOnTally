package com.tabsontally.markomarks.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tabsontally.markomarks.model.items.PageSizeItem;
import com.tabsontally.markomarks.tabsontally.R;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PageSizeItem pageSize = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_page_size_item, parent, false);
        }

        TextView pageSizeText = (TextView) convertView.findViewById(R.id.bill_page_size_item_id);
        pageSizeText.setText(pageSize.getmText());

        return convertView;
    }

    @Override
    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {

        PageSizeItem pageSize = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_page_size_item, parent, false);
        }

        TextView pageSizeText = (TextView) convertView.findViewById(R.id.bill_page_size_item_id);
        pageSizeText.setText(pageSize.getmText());

        return pageSizeText;
    }

}
