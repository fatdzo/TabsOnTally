package com.tabsontally.markomarks.arrayadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tabsontally.markomarks.tabsontally.BillDetailActivity;
import com.tabsontally.markomarks.model.items.BillItem;
import com.tabsontally.markomarks.tabsontally.R;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillAdapter extends ArrayAdapter<BillItem> {

    private Context ctx;

    private ListView legVotesListview;
    private LegislatorVoteAdapter legVotesAdapter;

    private LinearLayout billView;

    private int mCurrentPage = 1;
    private int mCurrentPageSize = 10;

    public BillAdapter(Context context, ArrayList<BillItem> items, int currentPage, int currentPageSize)
    {
        super(context, 0, items);
        ctx = context;
        mCurrentPage = currentPage;
        mCurrentPageSize = currentPageSize;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final BillItem bill = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_item, parent, false);
        }

        billView = (LinearLayout) convertView.findViewById(R.id.view_bill_item);
        billView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, BillDetailActivity.class);
                intent.putExtra("BillItem", bill);
                //intent.putExtra(EXTRA_MESSAGE, message);
                ctx.startActivity(intent);
            }
        });

        // Lookup view for data population
        TextView billIndex = (TextView) convertView.findViewById(R.id.txt_BillIndex);
        int index = (mCurrentPage - 1) * mCurrentPageSize + (position + 1);
        billIndex.setText(String.valueOf(index) + ". ");

        TextView billName = (TextView) convertView.findViewById(R.id.txt_BillName);
        billName.setText(bill.Title);

        TextView  billDescription = (TextView) convertView.findViewById(R.id.txt_BillDescription);
        billDescription.setText(bill.getDescription());

        legVotesAdapter = new LegislatorVoteAdapter(ctx, bill.Votes);

        legVotesListview = (ListView)convertView.findViewById(R.id.lst_legVotes);
        legVotesListview.setAdapter(legVotesAdapter);

        return convertView;
    }

}
