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
import com.tabsontally.markomarks.model.json.BillDetail;
import com.tabsontally.markomarks.model.items.BillItem;
import com.tabsontally.markomarks.model.items.PersonItem;
import com.tabsontally.markomarks.model.items.VoteItem;
import com.tabsontally.markomarks.routemanager.BillDetailManager;

import java.util.ArrayList;

public class BillDetailActivity extends BaseActivity {

    private BillVoterAdapter billVoterAdapter;
    ArrayList<VoteItem> billVoteItems = new ArrayList<>();

    TextView txtBillTitleView;
    TextView txtBillDescription;

    TextView txtbillIdentifier;

    TextView txtBillSubjects;
    TextView txtBillDates;

    BillDetailManager bllDetailManager;

    BillItem billSource;
    BillDetail billDetail;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BillDetailManager.PULL_SUCCESS:
                {
                    billDetail = bllDetailManager.getBillDetail();
                    if(billDetail != null)
                    {
                        txtBillTitleView.setText(billDetail.getTitle());
                        txtbillIdentifier.setText(billDetail.getIdentifier());

                        txtBillDates.setText(billDetail.getDatesString());
                        txtBillSubjects.setText(billDetail.getSubjectsString());
                        txtBillDescription.setText(billDetail.getDescription());
                    }

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initializeControls()
    {
        Intent i = getIntent();

        billSource = (BillItem)i.getSerializableExtra("BillItem");

        billVoteItems.addAll(billSource.Votes);

        ArrayList<PersonItem> personList = (ArrayList<PersonItem>) i.getSerializableExtra("PersonList");

        txtBillTitleView = (TextView) findViewById(R.id.txt_BillDetailTitle);
        txtBillTitleView.setText(billSource.Title);

        txtBillDescription = (TextView) findViewById(R.id.txt_BillDetailDescription);
        txtbillIdentifier = (TextView) findViewById(R.id.txt_BillDetailIdentifier);
        txtBillSubjects = (TextView) findViewById(R.id.txt_BillDetailSubjects);
        txtBillDates = (TextView) findViewById(R.id.txt_BillDetailDates);

        billVoterAdapter = new BillVoterAdapter(context, billVoteItems, personList);

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
                return "ad9608f7-d243-4f20-bc34-03c02c42792d";
            }

            @Override
            public String getApiKeyHeader() {
                return "X-APIKEY";
            }
        };

        bllDetailManager = new BillDetailManager(context, tabsApi);
        bllDetailManager.setBillId(billSource.Id);
        bllDetailManager.pullRecords();

    }

}
