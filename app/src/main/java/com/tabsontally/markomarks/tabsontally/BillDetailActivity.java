package com.tabsontally.markomarks.tabsontally;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BillDetailActivity extends AppCompatActivity {

    private BillVoteAdapter billVoteAdapter;
    private Context context;
    ArrayList<BillVoteItem> billVoteItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        context = this;


        initializeControls();


    }

    private void initializeControls()
    {
        BillVoteItem temp = new BillVoteItem();
        billVoteItems.add(temp);



        billVoteAdapter = new BillVoteAdapter(context, billVoteItems);

        ListView lstVoters =  (ListView) findViewById(R.id.lst_Voters);
        lstVoters.setAdapter(billVoteAdapter);

    }

}
