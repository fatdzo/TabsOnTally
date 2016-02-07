package com.tabsontally.markomarks.tabsontally;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.tabsontally.markomarks.arrayadapters.BillVoterAdapter;
import com.tabsontally.markomarks.model.items.BillItem;
import com.tabsontally.markomarks.model.items.VoteItem;

import java.util.ArrayList;

public class BillDetailActivity extends AppCompatActivity {

    private BillVoterAdapter billVoterAdapter;
    private Context context;
    ArrayList<VoteItem> billVoteItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        context = this;


        initializeControls();


    }

    private void initializeControls()
    {
        Intent i = getIntent();

        BillItem bll = (BillItem)i.getSerializableExtra("BillItem");
        billVoteItems.addAll(bll.Votes);

        TextView billIndex = (TextView) findViewById(R.id.txt_BillDetailTitle);
        billIndex.setText(bll.Title);

        TextView billDescription = (TextView) findViewById(R.id.txt_BillDetailDescription);
        billDescription.setText(bll.getDescription());


        billVoterAdapter = new BillVoterAdapter(context, billVoteItems);

        //legVoteAdapter = new LegislatorVoteAdapter(ctx, bill.Votes);

        //legVotesListView = (ListView) convertView.findViewById(R.id.lst_legVotes);
        //legVotesListView.setAdapter(legVoteAdapter);

        ListView lstVoters =  (ListView) findViewById(R.id.lst_Voters);
        lstVoters.setAdapter(billVoterAdapter);

    }

}
