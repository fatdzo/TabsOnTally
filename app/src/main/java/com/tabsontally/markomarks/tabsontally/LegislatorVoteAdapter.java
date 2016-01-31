package com.tabsontally.markomarks.tabsontally;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 1/24/2016.
 */
public class LegislatorVoteAdapter extends ArrayAdapter<VoteItem> {

    private Context ctx;
    private LinearLayout lin_LegVote;

    public LegislatorVoteAdapter(Context context, ArrayList<VoteItem> items)
    {
        super(context, 0, items);
        ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        VoteItem vote = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.legislator_vote_item, parent, false);
        }


        lin_LegVote = (LinearLayout) convertView.findViewById(R.id.lin_legVoteResult);

        // Lookup view for data population
        TextView voteResult = (TextView) convertView.findViewById(R.id.txt_legislatorVoteResult);
        if(vote.PersonVoted)
        {
            lin_LegVote.setBackgroundColor(Color.GREEN);
        }
        else
        {
            lin_LegVote.setBackgroundColor(Color.RED);
        }
        voteResult.setText(String.valueOf(vote.getPersonVotedString()));

        return convertView;
    }
}
