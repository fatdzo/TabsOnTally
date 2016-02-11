package com.tabsontally.markomarks.tabsontally;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.tabsontally.markomarks.arrayadapters.BillVoterAdapter;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.BillDetail;
import com.tabsontally.markomarks.model.items.BillItem;
import com.tabsontally.markomarks.model.items.VoteItem;
import com.tabsontally.markomarks.routemanager.BillDetailManager;
import com.tabsontally.markomarks.routemanager.BillManager;
import com.tabsontally.markomarks.routemanager.PeopleManager;
import com.tabsontally.markomarks.routemanager.VoteManager;

import java.util.ArrayList;

public class BillDetailActivity extends AppCompatActivity {

    private BillVoterAdapter billVoterAdapter;
    private Context context;
    ArrayList<VoteItem> billVoteItems = new ArrayList<>();
    private APIConfig tabsApi;

    LocalBroadcastManager broadcastManager;

    TextView billTitleView;
    TextView billDescription;

    BillDetailManager bllDetailManager;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BillDetailManager.PULL_SUCCESS:
                {
                    BillDetail detail = bllDetailManager.getBillDetail();
                    billTitleView.setText(detail.getmTitle());

                }break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        context = this;

        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BillDetailManager.PULL_SUCCESS);
        broadcastManager.registerReceiver(mBroadcastReceiver, filter);

        initializeControls();


    }

    private void initializeControls()
    {
        Intent i = getIntent();

        BillItem bll = (BillItem)i.getSerializableExtra("BillItem");
        billVoteItems.addAll(bll.Votes);

        billTitleView = (TextView) findViewById(R.id.txt_BillDetailTitle);
        billTitleView.setText(bll.Title);

        billDescription = (TextView) findViewById(R.id.txt_BillDetailDescription);
        billDescription.setText(bll.getDescription());

        billVoterAdapter = new BillVoterAdapter(context, billVoteItems);

        //legVoteAdapter = new LegislatorVoteAdapter(ctx, bill.Votes);

        //legVotesListView = (ListView) convertView.findViewById(R.id.lst_legVotes);
        //legVotesListView.setAdapter(legVoteAdapter);

        ListView lstVoters =  (ListView) findViewById(R.id.lst_Voters);
        lstVoters.setAdapter(billVoterAdapter);

        tabsApi = new APIConfig() {
            @Override
            public String getUrl() {
                return "https://tabsontallahassee.com/api/";
            }

            @Override
            public String getApiKey() {
                return "2dac3769-82a8-43e4-879c-2b774cfe1328";
            }

            @Override
            public String getApiKeyHeader() {
                return "X-APIKEY";
            }
        };

        bllDetailManager = new BillDetailManager(context, tabsApi);
        bllDetailManager.setBillId(bll.Id);
        bllDetailManager.pullRecords();

    }

}
