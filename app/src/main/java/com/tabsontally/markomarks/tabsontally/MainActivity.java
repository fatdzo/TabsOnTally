package com.tabsontally.markomarks.tabsontally;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tabsontally.markomarks.routemanager.BillManager;
import com.tabsontally.markomarks.routemanager.LegislatorVotingOption;
import com.tabsontally.markomarks.routemanager.PeopleManager;
import com.tabsontally.markomarks.routemanager.VoteManager;
import com.tabsontally.markomarks.arrayadapters.BillAdapter;
import com.tabsontally.markomarks.json.MetaDeserializer;
import com.tabsontally.markomarks.json.VoteDeserializer;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.Meta;
import com.tabsontally.markomarks.model.Vote;
import com.tabsontally.markomarks.model.items.BillItem;
import com.tabsontally.markomarks.model.items.PersonItem;
import com.tabsontally.markomarks.model.items.VoteItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private Context context;
    private BillAdapter billAdapter;

    GoogleApiClient mGoogleApiClient;

    private ArrayList<BillItem> billList = new ArrayList<>();
    private ArrayList<PersonItem> personList = new ArrayList<>();

    private APIConfig tabsApi;

    private BillManager bllManager;
    private PeopleManager pplManager;
    //private VoteManager vtManager;
    private ArrayList<VoteManager> vtManagerList = new ArrayList<>();

    private ListView billsListView;

    private TextView txtCurrentPage;

    private int CurrentPage = 1;
    private int MaxPage = 1;
    private int MinPage = 1;
    private int PageSize = 20;

    private Button btn_PrevPageButton;
    private Button btn_NextPageButton;
    private Button btn_Prev5PagesButton;
    private Button btn_Next5PagesButton;

    private Location userLocation;

    LocalBroadcastManager broadcastManager;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case VoteManager.PULL_SUCCESS:{
                    getVoteItemList();
                    break;
                }
                case PeopleManager.PULL_SUCCESS: {
                    personList.clear();
                    personList.addAll(pplManager.getPersonItemList());

                    vtManagerList.clear();
                    final GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Vote.class, new VoteDeserializer());
                    gsonBuilder.registerTypeAdapter(Meta.class, new MetaDeserializer());
                    final Gson gson = gsonBuilder.create();

                    for (PersonItem p : personList) {

                        VoteManager temp = new VoteManager(context, tabsApi, gson);
                        temp.pullRecords(p.Id, p.FullName, LegislatorVotingOption.YES);
                        vtManagerList.add(temp);

                        VoteManager tempFalse = new VoteManager(context, tabsApi, gson);
                        tempFalse.pullRecords(p.Id, p.FullName, LegislatorVotingOption.NO);
                        vtManagerList.add(tempFalse);

                        VoteManager tempNotVoting = new VoteManager(context, tabsApi, gson);
                        tempNotVoting.pullRecords(p.Id, p.FullName, LegislatorVotingOption.NOTVOTING);
                        vtManagerList.add(tempNotVoting);
                    }


                    break;
                }
                case BillManager.PULL_SUCCESS: {
                    /*billList.clear();
                    billList.addAll(bllManager.getBillItemList());

                    billsListView.smoothScrollToPosition(0);
                    billAdapter.setAdapterList(billList);
                    if(bllManager.getmMeta()!=null)
                    {
                        MaxPage = bllManager.getmMeta().Pages;
                        CurrentPage = bllManager.getmMeta().Page;
                    }

                    vtManagerList.clear();
                    final GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Vote.class, new VoteDeserializer());
                    gsonBuilder.registerTypeAdapter(Meta.class, new MetaDeserializer());
                    final Gson gson = gsonBuilder.create();
                    for (PersonItem p : personList) {

                        VoteManager temp = new VoteManager(context, tabsApi, gson);
                        temp.pullRecords(p.Id, p.FullName, LegislatorVotingOption.YES);
                        vtManagerList.add(temp);

                        VoteManager tempFalse = new VoteManager(context, tabsApi, gson);
                        tempFalse.pullRecords(p.Id, p.FullName, LegislatorVotingOption.NO);
                        vtManagerList.add(tempFalse);

                        VoteManager tempNotVoting = new VoteManager(context, tabsApi, gson);
                        tempNotVoting.pullRecords(p.Id, p.FullName, LegislatorVotingOption.NOTVOTING);
                        vtManagerList.add(tempNotVoting);
                    }

                    break;
                    */
                }
            }
        }
    };

    private int billListContainsBillId(String billId)
    {

        for(int i=0; i< billList.size(); i++)
        {
            if(billList.get(i).Id.equals(billId))
            {
                return i;
            }

        }

        return -1;
    }


    private void getVoteItemList()
    {
        for(VoteManager vtManager: vtManagerList)
        {
            ArrayList<VoteItem> vItems = vtManager.getVoteItems();
            if(vItems.size() > 0)
            {
                for(VoteItem v: vItems)
                {
                    int foundBillIndex = billListContainsBillId(v.BillId);

                    if(foundBillIndex >= 0)
                    {
                        int foundVoteIndex = billList.get(foundBillIndex).getVoteIndex(v);
                        if(foundVoteIndex >= 0)
                        {
                            if(billList.get(foundBillIndex).containsVote(v)) {
                                VoteItem foundVote = billList.get(foundBillIndex).Votes.get(foundVoteIndex);
                                if(foundVote.Updated.before(v.Updated))
                                {
                                    billList.get(foundBillIndex).Votes.remove(foundVoteIndex);
                                    billList.get(foundBillIndex).Votes.add(v);
                                }
                            }
                        }
                        else
                        {
                            billList.get(foundBillIndex).Votes.add(v);
                        }

                    }
                    else
                    {
                        BillItem temp = new BillItem();
                        temp.Id = v.BillId;
                        temp.Title = v.BillId;
                        temp.UpdatedAt = v.Updated;
                        temp.Votes.add(v);
                        temp.Index = billList.size() + 1;
                        billList.add(temp);
                    }
                }

                billAdapter.notifyDataSetChanged();
            }
        }
    }




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

        Spinner spinner = (Spinner) findViewById(R.id.spinPageSize);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.bill_page_sizes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

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

        broadcastManager = LocalBroadcastManager.getInstance(this);
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

        pplManager = new PeopleManager(context, tabsApi);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
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
                //bllManager.pullRecordPage(CurrentPage);
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
                //bllManager.pullRecordPage(CurrentPage);

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
                //bllManager.pullRecordPage(CurrentPage);
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
                //bllManager.pullRecordPage(CurrentPage);
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
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
