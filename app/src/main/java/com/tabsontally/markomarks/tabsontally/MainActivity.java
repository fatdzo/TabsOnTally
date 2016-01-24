package com.tabsontally.markomarks.tabsontally;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.tabsontally.markomarks.RouteManager.BillManager;
import com.tabsontally.markomarks.RouteManager.PeopleManager;
import com.tabsontally.markomarks.RouteManager.VoteManager;
import com.tabsontally.markomarks.model.APIConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private Context context;
    private BillAdapter billAdapter;

    GoogleApiClient mGoogleApiClient;

    private ArrayList<BillItem> billList = new ArrayList<>();
    private ArrayList<PersonItem> personList = new ArrayList<>();
    private ArrayList<VoteItem> voteItemsList = new ArrayList<>();

    private APIConfig tabsApi;

    private BillManager bllManager;
    private PeopleManager pplManager;
    private VoteManager vtManager;

    private ListView billsListView;

    private TextView txtCurrentPage;

    private int CurrentPage = 1;
    private int MaxPage = 1;
    private int MinPage = 1;

    private Button btn_PrevPageButton;
    private Button btn_NextPageButton;
    private Button btn_Prev5PagesButton;
    private Button btn_Next5PagesButton;

    private Location userLocation;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case VoteManager.PULL_SUCCESS:{
                    ArrayList<VoteItem> vItems = vtManager.getVoteItems();
                    if(vItems.size() > 0)
                    {
                        voteItemsList.addAll(vItems);

                        for(int i=0; i < billList.size(); i++)
                        {
                            billList.get(i).Votes.clear();
                            for(VoteItem v: vItems)
                            {
                                if(billList.get(i).Id.equals(v.BillId))
                                {
                                    VoteItem temp = new VoteItem();
                                    temp.Result = v.Result;
                                    temp.Id = v.Id;
                                    temp.BillId = v.BillId;
                                    temp.PersonId = v.PersonId;
                                    billList.get(i).Votes.add(temp);
                                }
                            }

                        }

                        billAdapter.notifyDataSetChanged();

                    }

                    break;
                }
                case PeopleManager.PULL_SUCCESS: {
                    personList.clear();
                    personList.addAll(pplManager.getPersonItemList());

                    voteItemsList.clear();

                    vtManager.clearData();
                    for(BillItem b: billList) {

                        for (PersonItem p : personList) {
                            vtManager.pullRecords(b.Id, p.Id);
                        }
                    }


                    break;
                }
                case BillManager.PULL_SUCCESS: {
                    voteItemsList.clear();
                    billList.clear();
                    billList.addAll(bllManager.getBillItemList());

                    billsListView.smoothScrollToPosition(0);
                    billAdapter.setAdapterList(billList);
                    if(bllManager.getmMeta()!=null)
                    {
                        MaxPage = bllManager.getmMeta().Pages;
                        CurrentPage = bllManager.getmMeta().Page;
                    }


                    vtManager = new VoteManager(context, tabsApi);

                    for(BillItem b: billList) {
                        for (PersonItem p : personList) {
                            vtManager.pullRecords(b.Id, p.Id);
                        }
                    }

                    break;
                }
            }
        }
    };

    private int get5NextPages()
    {
        if(CurrentPage + 5 > MaxPage)
        {
            return MaxPage;
        }

        return CurrentPage + 5;
    }


    private int getNextPage()
    {
        if(CurrentPage < MaxPage)
        {
            return CurrentPage + 1;
        }

        return MaxPage;

    }

    private int getPrev5Pages()
    {
        if(CurrentPage - 5 > MinPage)
        {
            return CurrentPage - 5;
        }

        return MinPage;
    }


    private int getPreviousPage()
    {
        if(CurrentPage > MinPage + 1)
        {
            return CurrentPage - 1;
        }

        return MinPage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        InitializeControls();

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

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(PeopleManager.PULL_SUCCESS);
        filter.addAction(BillManager.PULL_SUCCESS);
        filter.addAction(VoteManager.PULL_SUCCESS);
        broadcastManager.registerReceiver(mBroadcastReceiver, filter);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        bllManager = new BillManager(context, tabsApi);

        vtManager = new VoteManager(context, tabsApi);

        pplManager = new PeopleManager(context, tabsApi);
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void InitializeControls()
    {
        billsListView = (ListView)findViewById(R.id.lst_Bills);
        billAdapter = new BillAdapter(context, billList);
        billsListView.setAdapter(billAdapter);

        txtCurrentPage = (TextView)findViewById(R.id.txt_currentPage);
        txtCurrentPage.setText(String.valueOf(CurrentPage));

        btn_NextPageButton = (Button)findViewById(R.id.btn_nextPage);

        btn_Next5PagesButton = (Button)findViewById(R.id.btn_next5Pages);

        btn_PrevPageButton = (Button)findViewById(R.id.btn_prevPage);
        btn_PrevPageButton.setEnabled(false);

        btn_Prev5PagesButton = (Button)findViewById(R.id.btn_prev5Pages);
        btn_Prev5PagesButton.setEnabled(false);

        btn_Next5PagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CurrentPage = get5NextPages();
                bllManager.pullRecordPage(CurrentPage);
                if (CurrentPage == MaxPage) {
                    btn_NextPageButton.setEnabled(false);
                    btn_Next5PagesButton.setEnabled(false);
                }

                btn_PrevPageButton.setEnabled(true);
                btn_Prev5PagesButton.setEnabled(true);

                txtCurrentPage.setText(String.valueOf(CurrentPage));
            }
        });

        btn_NextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPage = getNextPage();
                bllManager.pullRecordPage(CurrentPage);

                if(CurrentPage == MaxPage)
                {
                    btn_NextPageButton.setEnabled(false);
                    btn_Next5PagesButton.setEnabled(false);
                }

                btn_PrevPageButton.setEnabled(true);
                btn_Prev5PagesButton.setEnabled(true);

                txtCurrentPage.setText(String.valueOf(CurrentPage));
            }
        });


        btn_Prev5PagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPage = getPrev5Pages();
                bllManager.pullRecordPage(CurrentPage);
                if (CurrentPage == 1) {
                    btn_PrevPageButton.setEnabled(false);
                    btn_Prev5PagesButton.setEnabled(false);
                }

                btn_NextPageButton.setEnabled(true);
                btn_Next5PagesButton.setEnabled(true);

                txtCurrentPage.setText(String.valueOf(CurrentPage));
            }
        });

        btn_PrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPage = getPreviousPage();
                bllManager.pullRecordPage(CurrentPage);
                if(CurrentPage == 1)
                {
                    btn_PrevPageButton.setEnabled(false);
                    btn_Prev5PagesButton.setEnabled(false);
                }

                btn_NextPageButton.setEnabled(true);
                btn_Next5PagesButton.setEnabled(true);

                txtCurrentPage.setText(String.valueOf(CurrentPage));
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        userLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(userLocation!=null)
        {
            pplManager.pullPeopleFromLatLong(userLocation.getLatitude(), userLocation.getLongitude());
            voteItemsList.clear();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
