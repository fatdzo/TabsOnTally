package com.tabsontally.markomarks.tabsontally;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillAdapter extends ArrayAdapter<BillItem> {

    public BillAdapter(Context context, ArrayList<BillItem> items)
    {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BillItem bill = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_item, parent, false);
        }
        // Lookup view for data population
        TextView billIndex = (TextView) convertView.findViewById(R.id.txt_BillIndex);
        billIndex.setText(String.valueOf(bill.Index) + ". ");
        TextView billName = (TextView) convertView.findViewById(R.id.txt_BillName);
        billName.setText(bill.Title);

        TextView billVote = (TextView) convertView.findViewById(R.id.txt_BillVote);
        billVote.setText(bill.Vote);
        return convertView;
    }

}
