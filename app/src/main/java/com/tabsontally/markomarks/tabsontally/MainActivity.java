package com.tabsontally.markomarks.tabsontally;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Utilities utilities = new Utilities();
    private Context context;
    private BillAdapter billAdapter;

    private ArrayList<BillItem> billList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        InitializeControls();


    }

    private void InitializeControls()
    {
        ListView billsList = (ListView)findViewById(R.id.lst_Bills);

        BillItem temp = new BillItem();
        temp.Index = 1;
        temp.Title = "TabsOnTally";
        temp.Vote = "NotVoted";

        billList.add(temp);


        billAdapter = new BillAdapter(context, billList);
        billsList.setAdapter(billAdapter);
    }

}
