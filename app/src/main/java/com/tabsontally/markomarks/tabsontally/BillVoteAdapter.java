package com.tabsontally.markomarks.tabsontally;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillVoteAdapter extends ArrayAdapter<BillVoteItem> {

    private Context ctx;
    private LegislatorVoteAdapter legVoteAdapter;
    private ListView legVotesListView;

    public BillVoteAdapter(Context context, ArrayList<BillVoteItem> items)
    {
        super(context, 0, items);
        ctx = context;
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

        legVoteAdapter = new LegislatorVoteAdapter(ctx, bill.Votes);

        legVotesListView = (ListView) convertView.findViewById(R.id.lst_legVotes);
        legVotesListView.setAdapter(legVoteAdapter);

        return convertView;
    }

}
