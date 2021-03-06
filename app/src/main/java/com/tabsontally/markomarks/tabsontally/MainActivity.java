package com.tabsontally.markomarks.tabsontally;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tabsontally.markomarks.arrayadapters.BillPageSizeAdapter;
import com.tabsontally.markomarks.arrayadapters.BillSortOptionAdapter;
import com.tabsontally.markomarks.model.json.BillDetail;
import com.tabsontally.markomarks.model.json.PersonDetails;
import com.tabsontally.markomarks.model.db.BillDetailDB;
import com.tabsontally.markomarks.model.items.BaseItem;
import com.tabsontally.markomarks.model.items.PageSizeItem;
import com.tabsontally.markomarks.routemanager.BillDetailManager;
import com.tabsontally.markomarks.routemanager.BillManager;
import com.tabsontally.markomarks.model.LegislatorVotingOption;
import com.tabsontally.markomarks.routemanager.PeopleManager;
import com.tabsontally.markomarks.routemanager.PersonDetailsManager;
import com.tabsontally.markomarks.routemanager.VoteManager;
import com.tabsontally.markomarks.arrayadapters.BillAdapter;
import com.tabsontally.markomarks.jsondeserializers.MetaDeserializer;
import com.tabsontally.markomarks.jsondeserializers.VoteDeserializer;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.json.Meta;
import com.tabsontally.markomarks.model.json.Vote;
import com.tabsontally.markomarks.model.items.BillItem;
import com.tabsontally.markomarks.model.items.PersonItem;
import com.tabsontally.markomarks.model.items.VoteItem;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    public static final int SORTBYDATE = 0;
    public static final int SORTBYVOTES = 1;
    public static final int SORTBYNAME = 2;

    private int CurrentSort = SORTBYDATE;

    private BillAdapter billAdapter;

    GoogleApiClient mGoogleApiClient;

    private ArrayList<BillDetailManager> billDetailManagers = new ArrayList<>();
    private ArrayList<PersonDetailsManager> personDetailManages = new ArrayList<>();
    private ArrayList<VoteManager> voteManagers = new ArrayList<>();
    private Gson voteGson;

    private PeopleManager pplManager;

    private ListView billsListView;
    private ArrayList<PersonItem> personList = new ArrayList<>();
    private ArrayList<BillItem> billList = new ArrayList<>();

    private int CurrentPage = 1;
    private int MaxPage = 1;
    private int MinPage = 1;
    private int PageSize = 20;

    private int VoteManagerCurrentPage = 1;

    private TextView txtCurrentPage;

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
                    getVoteItemList();
                    break;
                }
                case PersonDetailsManager.PULL_SUCCESS:{
                    for(PersonDetailsManager pdm: personDetailManages)
                    {
                        if(!pdm.isManagerDone())
                        {
                            PersonDetails tempDetails = pdm.getPersonDetails();

                            if(tempDetails!=null)
                            {
                                for(PersonItem p: personList)
                                {
                                    if(pdm.getmPersonId().equals(p.Id))
                                    {
                                        p.Details = tempDetails;
                                        personItemDataManager.addPersonDetailToContent(context, p.Details);
                                    }
                                }
                            }
                        }
                    }

                    billAdapter.updatePersonList(personList);
                    break;
                }
                case PeopleManager.PULL_SUCCESS: {
                    personList.clear();
                    voteManagers.clear();
                    personList.addAll(pplManager.getPersonItemList());

                    for (PersonItem p : personList) {
                        if(p.Details == null)
                        {
                            PersonDetailsManager pdm = new PersonDetailsManager(context, tabsApi, p.Id);
                            pdm.pullRecords();

                            personDetailManages.add(pdm);
                        }

                        addVoteManagersForPerson(voteGson, p.Id, p.FullName);
                    }
                    break;
                }
                case BillDetailManager.PULL_SUCCESS:{
                    for(BillDetailManager bdm: billDetailManagers)
                    {
                        if(!bdm.isManagerDone())
                        {
                            BillDetail billDetail = bdm.getBillDetail();
                            if(billDetail != null)
                            {
                                for(BillItem b : billList)
                                {
                                    if(b.Id.equals(billDetail.getId()))
                                    {
                                        updateBillItemFromBillDetailDb(b, billDetailsDataManager.convertToBillDetailDB(billDetail));
                                        billDetailsDataManager.addBillDetailToContent(context, billDetail);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    refreshBillListAndPaginate();
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

                    voteManagers.clear();
                    for (PersonItem p : personList) {

                        VoteManager temp = new VoteManager(context, tabsApi, gson);
                        temp.pullRecords(p.Id, p.FullName, LegislatorVotingOption.YES);
                        voteManagers.add(temp);

                        VoteManager tempFalse = new VoteManager(context, tabsApi, gson);
                        tempFalse.pullRecords(p.Id, p.FullName, LegislatorVotingOption.NO);
                        voteManagers.add(tempFalse);

                        VoteManager tempNotVoting = new VoteManager(context, tabsApi, gson);
                        tempNotVoting.pullRecords(p.Id, p.FullName, LegislatorVotingOption.NOTVOTING);
                        voteManagers.add(tempNotVoting);
                    }

                    break;
                    */
                }
            }
        }
    };

    private void pullVotesForPage()
    {
        if(MaxPage > 1 && MaxPage - CurrentPage < 2)
        {
            VoteManagerCurrentPage = VoteManagerCurrentPage + 1;
            for(VoteManager vt: voteManagers)
            {
                vt.pullRecordsForPage(VoteManagerCurrentPage);
            }
        }

    }

    private void addVoteManagersForPerson(Gson gson, String personId, String personFullName)
    {
        if(!voteManagerListContainsPersonId(personId))
        {
            VoteManager temp = new VoteManager(context, tabsApi, gson);
            temp.pullRecords(personId, personFullName, LegislatorVotingOption.YES, CurrentPage);
            voteManagers.add(temp);

            VoteManager tempFalse = new VoteManager(context, tabsApi, gson);
            tempFalse.pullRecords(personId, personFullName, LegislatorVotingOption.NO, CurrentPage);
            voteManagers.add(tempFalse);

            VoteManager tempNotVoting = new VoteManager(context, tabsApi, gson);
            tempNotVoting.pullRecords(personId, personFullName, LegislatorVotingOption.NOTVOTING, CurrentPage);
            voteManagers.add(tempNotVoting);
        }
    }

    private void updateBillItemFromBillDetailDb(BillItem billItem, BillDetailDB detail)
    {
        billItem.Title = detail.getTitle();
    }

    private boolean voteManagerListContainsPersonId(String personId)
    {
        for(VoteManager v: voteManagers)
        {
            if(v.getmPersonId().equals(personId))
            {
                return true;
            }
        }
        return false;
    }


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
        for(VoteManager vtManager: voteManagers)
        {
            ArrayList<VoteItem> vItems = vtManager.getVoteItems();
            if(vItems.size() > 0) {
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

                        BillDetailDB bd = getBillDetailFromContent(v.BillId);
                        if(bd != null)
                        {
                            updateBillItemFromBillDetailDb(temp, bd);
                            //TODO: load more data for the bill item
                        }
                        temp.UpdatedAt = v.Updated;
                        temp.Votes.add(v);
                        temp.Index = billList.size() + 1;
                        billList.add(temp);
                    }
                }
            }
        }

        refreshBillListAndPaginate();
    }


    private BillDetailDB getBillDetailFromContent(String billId)
    {
        BillDetailDB foundBd = billDetailsDataManager.findDetailDbById(billId);

        if(foundBd != null)
        {
            return foundBd;
        }

        //No Results found time to call the download details manager
        BillDetailManager temp = new BillDetailManager(context, tabsApi);
        temp.setBillId(billId);
        temp.pullRecords();
        billDetailManagers.add(temp);

        return null;
    }


    private void refreshBillListAndPaginate()
    {
        ArrayList<BillItem> displayList = new ArrayList<>();

        for(BillItem bl: billList)
        {
            if(bl.Title != null && bl.Title.length() > 0)
            {
                displayList.add(bl);
            }
        }

        MaxPage = (int) Math.ceil((double) displayList.size() / PageSize);
        txtCurrentPage.setText(String.valueOf(CurrentPage) + "/" + String.valueOf(MaxPage));

        btn_PrevPageButton.setEnabled(true);
        btn_Prev5PagesButton.setEnabled(true);

        if(CurrentPage == 1)
        {
            btn_PrevPageButton.setEnabled(false);
            btn_Prev5PagesButton.setEnabled(false);
        }

        btn_NextPageButton.setEnabled(true);
        btn_Next5PagesButton.setEnabled(true);

        if(CurrentPage == MaxPage)
        {
            btn_NextPageButton.setEnabled(false);
            btn_Next5PagesButton.setEnabled(false);
        }

        if(CurrentPage > 0 && PageSize > 0 && displayList.size() > 0)
        {
            int maxValue = CurrentPage * PageSize;
            if(maxValue > displayList.size() - 1)
            {
                maxValue = displayList.size() - 1;
            }

            int minValue = (CurrentPage - 1) * PageSize;

            if(CurrentSort == SORTBYDATE)
            {
                Collections.sort(displayList, BillItem.UpdatedAtComparator);
            }
            if(CurrentSort == SORTBYNAME)
            {
                Collections.sort(displayList, BillItem.TitleComparator);
            }
            if(CurrentSort == SORTBYVOTES)
            {
                Collections.sort(displayList, BillItem.BillVotesComparator);
            }

            ArrayList<BillItem> resultList = new ArrayList<>(displayList.subList(minValue, maxValue));

            billAdapter = new BillAdapter(context, resultList, personList, CurrentPage, PageSize);
            billsListView.setAdapter(billAdapter);
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
        if(CurrentPage - 5 > MinPage) {
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

        if(savedInstanceState == null)
        {
            context = MainActivity.this;

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

            broadcastManager = LocalBroadcastManager.getInstance(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction(PeopleManager.PULL_SUCCESS);
            filter.addAction(BillManager.PULL_SUCCESS);
            filter.addAction(BillDetailManager.PULL_SUCCESS);
            filter.addAction(VoteManager.PULL_SUCCESS);
            filter.addAction(PersonDetailsManager.PULL_SUCCESS);
            broadcastManager.registerReceiver(mBroadcastReceiver, filter);

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            pplManager = new PeopleManager(context, tabsApi);

            billDetailsDataManager.loadUserFileData(context);

            personItemDataManager.loadUserFileData(context);
            personList = personItemDataManager.getPersonItems();

            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Vote.class, new VoteDeserializer());
            gsonBuilder.registerTypeAdapter(Meta.class, new MetaDeserializer());
            voteGson = gsonBuilder.create();

            for (PersonItem p : personList) {
                if(p.Details == null)
                {
                    PersonDetailsManager pdm = new PersonDetailsManager(context, tabsApi, p.Id);
                    pdm.pullRecords();

                    personDetailManages.add(pdm);
                }

                addVoteManagersForPerson(voteGson, p.Id, p.FullName);
            }

            if(userLocation != null && personList.size() == 0)
            {
                pplManager.pullPeopleFromLatLong(userLocation.getLatitude(), userLocation.getLongitude());
            }
            else if (personList.size() == 0)
            {
                pplManager.pullPeopleFromLatLong(28.43, -81.36);
            }

        }

        InitializeControls();
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
        billAdapter = new BillAdapter(context, billList, personList, CurrentPage, PageSize);
        billsListView.setAdapter(billAdapter);

        txtCurrentPage = (TextView)findViewById(R.id.txt_currentPage);
        txtCurrentPage.setText(String.valueOf(CurrentPage) + "/" + String.valueOf(MaxPage));

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

                pullVotesForPage();

                refreshBillListAndPaginate();
                //bllManager.pullRecordPage(CurrentPage);
            }
        });

        btn_NextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPage = getNextPage();

                pullVotesForPage();

                refreshBillListAndPaginate();
                //bllManager.pullRecordPage(CurrentPage);
            }
        });


        btn_Prev5PagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPage = getPrev5Pages();

                pullVotesForPage();

                refreshBillListAndPaginate();
            }
        });

        btn_PrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPage = getPreviousPage();

                pullVotesForPage();

                refreshBillListAndPaginate();
                //bllManager.pullRecordPage(CurrentPage);
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinPageSize);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayList<PageSizeItem> pageSizes = new ArrayList<>();
        PageSizeItem temp10 = new PageSizeItem(10);
        PageSizeItem temp20 = new PageSizeItem(20);
        PageSizeItem temp50 = new PageSizeItem(50);
        pageSizes.add(temp10);
        pageSizes.add(temp20);
        pageSizes.add(temp50);
        BillPageSizeAdapter pageSizeAdapter = new BillPageSizeAdapter(context, pageSizes);
        spinner.setAdapter(pageSizeAdapter);

        PageSize = 10;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                PageSizeItem selected = (PageSizeItem) parent.getSelectedItem();
                CurrentPage = 1;
                PageSize = selected.getmvalue();

                pullVotesForPage();

                refreshBillListAndPaginate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerSort = (Spinner) findViewById(R.id.spinSort);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayList<BaseItem> sortOptions = new ArrayList<>();
        BaseItem dateSort = new BaseItem(SORTBYDATE,"by date");
        BaseItem voteSort = new BaseItem(SORTBYVOTES, "by votes");
        BaseItem nameSort = new BaseItem(SORTBYNAME, "by name");
        sortOptions.add(dateSort);
        sortOptions.add(nameSort);
        sortOptions.add(voteSort);
        BillSortOptionAdapter pageSortAdapter = new BillSortOptionAdapter(context, sortOptions);
        spinnerSort.setAdapter(pageSortAdapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BaseItem selected = (BaseItem) parent.getSelectedItem();

                CurrentSort = selected.getmvalue();

                CurrentPage = 1;

                refreshBillListAndPaginate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onConnected(Bundle bundle) {
        userLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}