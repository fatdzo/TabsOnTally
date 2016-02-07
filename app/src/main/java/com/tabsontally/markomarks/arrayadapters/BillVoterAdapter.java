package com.tabsontally.markomarks.arrayadapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tabsontally.markomarks.routemanager.LegislatorVotingOption;
import com.tabsontally.markomarks.tabsontally.R;
import com.tabsontally.markomarks.model.items.VoteItem;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillVoterAdapter extends ArrayAdapter<VoteItem> {

    private Context ctx;
    private LegislatorVoteAdapter legVoteAdapter;
    private ListView legVotesListView;

    public BillVoterAdapter(Context context, ArrayList<VoteItem> items)
    {
        super(context, 0, items);
        ctx = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        VoteItem bill = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_voter_item, parent, false);
        }

        // Lookup view for data population
        TextView billIndex = (TextView) convertView.findViewById(R.id.txt_BillDetailVoterIndex);
        billIndex.setText(String.valueOf(position + 1) + ". ");
        TextView billName = (TextView) convertView.findViewById(R.id.txt_BillDetailVoterName);
        billName.setText(bill.Name);

        LinearLayout billVoteLay = (LinearLayout) convertView.findViewById(R.id.lin_BillDetailVoterVoteLayout);

        TextView billVote = (TextView) convertView.findViewById(R.id.txt_BillDetailVoterVote);
        billVote.setText(String.valueOf(bill.getPersonVotedString()));

        if(bill.PersonVotingOption == LegislatorVotingOption.YES)
        {
            billVoteLay.setBackgroundColor(Color.GREEN);
        }
        else if(bill.PersonVotingOption == LegislatorVotingOption.NO)
        {
            billVoteLay.setBackgroundColor(Color.RED);
        }
        else if(bill.PersonVotingOption == LegislatorVotingOption.NOTVOTING)
        {
            billVoteLay.setBackgroundColor(Color.BLUE);
        }


        return convertView;
    }

}
