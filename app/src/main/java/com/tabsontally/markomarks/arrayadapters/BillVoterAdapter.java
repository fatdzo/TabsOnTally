package com.tabsontally.markomarks.arrayadapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.tabsontally.markomarks.model.LegislatorVotingOption;
import com.tabsontally.markomarks.model.Person;
import com.tabsontally.markomarks.model.items.PersonItem;
import com.tabsontally.markomarks.tabsontally.R;
import com.tabsontally.markomarks.model.items.VoteItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillVoterAdapter extends ArrayAdapter<VoteItem> {

    private Context ctx;

    ArrayList<PersonItem> mPersons;
    ImageView legImage;

    public BillVoterAdapter(Context context, ArrayList<VoteItem> votes, ArrayList<PersonItem> persons)
    {
        super(context, 0, votes);
        ctx = context;
        mPersons = persons;
    }


    public PersonItem findPerson(String personId)
    {
        for(PersonItem p: mPersons)
        {
            if(p.Id.equals(personId))
            {
                return p;
            }
        }

        return null;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        VoteItem vote = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_voter_item, parent, false);
        }

        // Lookup view for data population
        TextView billIndex = (TextView) convertView.findViewById(R.id.txt_BillDetailVoterIndex);
        billIndex.setText(String.valueOf(position + 1) + ". ");
        TextView billName = (TextView) convertView.findViewById(R.id.txt_BillDetailVoterName);
        billName.setText(vote.Name);

        LinearLayout billVoteLay = (LinearLayout) convertView.findViewById(R.id.lin_BillDetailVoterVoteLayout);

        TextView billVote = (TextView) convertView.findViewById(R.id.txt_BillDetailVoterVote);
        billVote.setText(String.valueOf(vote.getPersonVotedString()));

        legImage = (ImageView) convertView.findViewById(R.id.img_BillDetailVoterImage);
        legImage.setImageResource(R.drawable.places_ic_search);

        PersonItem person = findPerson(vote.PersonId);

        if(person != null && person.ImageUrl.length() > 0)
        {
            if(person.Details.getmContactDetails()!=null && person.Details.getmContactDetails().size() > 0)
            {
                Log.e("TABSONTALLY", "GOT DETAILS " + person.Details.getmContactDetails().get(0));
            }

            Log.e("TABSONTALLY", "GOT IMAGE " + person.ImageUrl );



            Ion.with(legImage)
                    .placeholder(R.drawable.places_ic_clear)
                    .smartSize(false)
                    .load(person.ImageUrl);

        }


        if(vote.PersonVotingOption == LegislatorVotingOption.YES)
        {
            billVoteLay.setBackgroundColor(Color.GREEN);
        }
        else if(vote.PersonVotingOption == LegislatorVotingOption.NO)
        {
            billVoteLay.setBackgroundColor(Color.RED);
        }
        else if(vote.PersonVotingOption == LegislatorVotingOption.NOTVOTING)
        {
            billVoteLay.setBackgroundColor(Color.BLUE);
        }


        return convertView;
    }

}
