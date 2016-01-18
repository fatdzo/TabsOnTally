package com.tabsontally.markomarks.tabsontally;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.tabsontally.markomarks.RouteManager.BillManager;
import com.tabsontally.markomarks.RouteManager.PeopleManager;
import com.tabsontally.markomarks.model.APIConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Utilities utilities = new Utilities();
    private Context context;
    private BillAdapter billAdapter;

    private ArrayList<BillItem> billList = new ArrayList<>();

    private BillManager bllManager;

    private ListView billsListView;


    PeopleManager mPeopleManager;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case PeopleManager.PULL_SUCCESS: {


                    break;
                }
                case BillManager.PULL_SUCCESS: {
                    Log.e("TABONTALLY", "SUCCCEESS");
                    billList.clear();
                    billList.addAll(bllManager.getBillAsAnArrayList());

                    billAdapter.setAdapterList(billList);

                    billAdapter.notifyDataSetChanged();


                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        InitializeControls();

        Log.e("TAGSFORTALLY", "TEST");


        APIConfig tabsApi = new APIConfig() {
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

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(PeopleManager.PULL_SUCCESS);
        filter.addAction(BillManager.PULL_SUCCESS);
        broadcastManager.registerReceiver(mBroadcastReceiver, filter);

        bllManager = new BillManager(context, tabsApi);


    }

    private void InitializeControls()
    {

      billsListView = (ListView)findViewById(R.id.lst_Bills);

        BillItem temp = new BillItem();
        temp.Index = 1;
        temp.Title = "Bill name";
        temp.Vote = "NotVoted";

        billList.add(temp);
        BillItem temp2 = new BillItem();
        temp2.Index = 1;
        temp2.Title = "Bill name 2";
        temp2.Vote = "Fail";
        billList.add(temp2);
        billList.add(temp);
        billList.add(temp2);

        BillItem temp3 = new BillItem();
        temp3.Index = 1;
        temp3.Title = "Bill name 3";
        temp3.Vote = "Pass";
        billList.add(temp3);


        billAdapter = new BillAdapter(context, billList);
        billsListView.setAdapter(billAdapter);
    }

}
