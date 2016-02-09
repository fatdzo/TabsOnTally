package com.tabsontally.markomarks.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tabsontally.markomarks.model.items.BaseItem;
import com.tabsontally.markomarks.tabsontally.R;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 2/8/2016.
 */
public class BillSortOptionAdapter  extends ArrayAdapter<BaseItem> {

    private Context ctx;

    public BillSortOptionAdapter(Context context, ArrayList<BaseItem> items)
    {
        super(context, 0, items);
        ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseItem sortOption = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_sort_option_item, parent, false);
        }

        TextView pageSizeText = (TextView) convertView.findViewById(R.id.bill_sort_option_item_id);
        pageSizeText.setText(sortOption.getmText());

        return convertView;
    }

    @Override
    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {

        BaseItem sortOption = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_sort_option_item, parent, false);
        }

        TextView pageSizeText = (TextView) convertView.findViewById(R.id.bill_sort_option_item_id);
        pageSizeText.setText(sortOption.getmText());

        return pageSizeText;
    }

}
