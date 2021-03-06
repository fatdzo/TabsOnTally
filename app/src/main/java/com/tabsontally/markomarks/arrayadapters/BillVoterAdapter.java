package com.tabsontally.markomarks.arrayadapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.tabsontally.markomarks.model.LegislatorVotingOption;
import com.tabsontally.markomarks.model.items.PersonItem;
import com.tabsontally.markomarks.tabsontally.R;
import com.tabsontally.markomarks.model.items.VoteItem;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class BillVoterAdapter extends ArrayAdapter<VoteItem> {

    private Context ctx;

    ArrayList<PersonItem> mPersons;
    ImageView legImage;
    ImageView contactDetailImage;
    TextView contactDetailName;

    Dialog contactDetailsDialog;
    ContactDetailsAdapter contactDetailsAdapter;


    ListView lst_contactDetails;

    public BillVoterAdapter(Context context, ArrayList<VoteItem> votes, ArrayList<PersonItem> persons)
    {
        super(context, 0, votes);
        ctx = context;
        mPersons = persons;
        initializeContactDialog();
    }

    public void initializeContactDialog()
    {
        // custom dialog
        contactDetailsDialog = new Dialog(ctx);
        contactDetailsDialog.setContentView(R.layout.dialog_billdetails_contactinfo);
        contactDetailsDialog.setTitle("Contact Details");


        Button dialogButton = (Button) contactDetailsDialog.findViewById(R.id.btn_contactDetails_closeDialog);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactDetailsDialog.dismiss();
            }
        });
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

        final PersonItem person = findPerson(vote.PersonId);

        lst_contactDetails = (ListView)contactDetailsDialog.findViewById(R.id.lst_ContactDetails);
        if(person != null )
        {
            if(person.ImageUrl.length() > 0)
            {
                Ion.with(legImage)
                        .placeholder(R.drawable.places_ic_clear)
                        .smartSize(false)
                        .load(person.ImageUrl);
            }

            if(person.Details!=null)
            {
                contactDetailsAdapter = new ContactDetailsAdapter(ctx, person.Details.getmContactDetails());
                lst_contactDetails.setAdapter(contactDetailsAdapter);
            }
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


        Button btnContact = (Button)convertView.findViewById(R.id.btn_Contact);

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contactDetailImage = (ImageView)contactDetailsDialog.findViewById(R.id.img_contactDetailImage);

                if(person.ImageUrl.length() > 0)
                {
                    Ion.with(contactDetailImage)
                            .placeholder(R.drawable.places_ic_clear)
                            .smartSize(false)
                            .load(person.ImageUrl);
                }

                if(person.Details!=null)
                {
                    contactDetailName = (TextView)contactDetailsDialog.findViewById(R.id.txt_contactDetailName);
                    contactDetailName.setText(person.Details.getmName());

                    contactDetailsAdapter = new ContactDetailsAdapter(ctx, person.Details.getmContactDetails());
                    lst_contactDetails.setAdapter(contactDetailsAdapter);
                }

                contactDetailsDialog.show();
            }
        });



        return convertView;
    }

}
