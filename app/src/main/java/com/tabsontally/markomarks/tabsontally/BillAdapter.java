package com.tabsontally.markomarks.tabsontally;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillAdapter extends ArrayAdapter<BillItem> {

    private Context ctx;

    private ArrayList<BillItem> itemz;

    public BillAdapter(Context context, ArrayList<BillItem> items)
    {
        super(context, 0, items);
        ctx = context;

        itemz = items;

    }

    public void setAdapterList(ArrayList<BillItem> bills)
    {
        itemz = bills;

        notifyDataSetChanged();
        Log.e("tag", "NUMBER OF ITEMS: " + itemz.size());

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BillItem bill = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_item, parent, false);
        }

        LinearLayout billView = (LinearLayout) convertView.findViewById(R.id.view_bill_item);
        billView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, BillDetailActivity.class);

                //intent.putExtra(EXTRA_MESSAGE, message);
                ctx.startActivity(intent);
            }
        });

        // Lookup view for data population
        TextView billIndex = (TextView) convertView.findViewById(R.id.txt_BillIndex);
        billIndex.setText(String.valueOf(bill.Index) + ". ");

        TextView billName = (TextView) convertView.findViewById(R.id.txt_BillName);
        billName.setText(bill.Title);

        TextView  billDescription = (TextView) convertView.findViewById(R.id.txt_BillDescription);
        billDescription.setText(bill.getDescription());

        TextView billVote = (TextView) convertView.findViewById(R.id.txt_BillVote);
        billVote.setText(bill.Vote);
        return convertView;
    }

}
