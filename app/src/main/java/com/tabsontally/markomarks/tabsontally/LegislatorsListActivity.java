package com.tabsontally.markomarks.tabsontally;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.tabsontally.markomarks.arrayadapters.LegislatorListAdapter;
import com.tabsontally.markomarks.dbManager.PersonItemDataManager;
import com.tabsontally.markomarks.model.APIConfig;
import com.tabsontally.markomarks.model.items.PersonItem;
import com.tabsontally.markomarks.routemanager.PeopleManager;

import java.util.ArrayList;
import java.util.Collections;

public class LegislatorsListActivity extends BaseActivity {
    PeopleManager peopleManager;
    LegislatorListAdapter legislatorListAdapter;
    ArrayList<PersonItem> savedPersons = new ArrayList<>();
    ArrayList<PersonItem> allPersons = new ArrayList<>();

    ListView lstLegislators;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case PeopleManager.PULL_SUCCESS:
                {
                    ArrayList<PersonItem> result = peopleManager.getPersonItemList();
                    for(PersonItem r: result)
                    {
                        if(savedPersons.contains(r))
                        {
                            r.IsSelected = true;
                        }

                        if(!allPersons.contains(r))
                        {
                            allPersons.add(r);
                        }
                    }

                    Collections.sort(allPersons, PersonItem.IsSelectedComparator);

                    legislatorListAdapter.notifyDataSetChanged();
                }break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislators_list);

        context = LegislatorsListActivity.this;

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
        broadcastManager.registerReceiver(mBroadcastReceiver, filter);

        personItemDataManager = new PersonItemDataManager();
        personItemDataManager.loadUserFileData(context);

        savedPersons = personItemDataManager.getPersonItems();

        peopleManager = new PeopleManager(context, tabsApi);
        peopleManager.pullAllPeople();

        legislatorListAdapter = new LegislatorListAdapter(context, allPersons, savedPersons);

        lstLegislators = (ListView) findViewById(R.id.lst_legislator_list);
        lstLegislators.setAdapter(legislatorListAdapter);


        EditText legSearch = (EditText) findViewById(R.id.edt_legislator);

        legSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                legislatorListAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}
