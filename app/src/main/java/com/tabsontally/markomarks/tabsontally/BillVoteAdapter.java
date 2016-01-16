package com.tabsontally.markomarks.tabsontally;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillVoteAdapter extends ArrayAdapter<BillVoteItem> {

    public BillVoteAdapter(Context context, ArrayList<BillVoteItem> items)
    {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BillVoteItem bill = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_voter_item, parent, false);
        }
        // Lookup view for data population
        TextView billIndex = (TextView) convertView.findViewById(R.id.txt_BillDetailVoteIndex);
        billIndex.setText(String.valueOf(bill.Index) + ". ");
        TextView billName = (TextView) convertView.findViewById(R.id.txt_BillDetailVoteName);
        billName.setText(bill.FullName);

        TextView billVote = (TextView) convertView.findViewById(R.id.txt_BillDetailVoteVote);
        billVote.setText(bill.Vote);
        return convertView;
    }

}
