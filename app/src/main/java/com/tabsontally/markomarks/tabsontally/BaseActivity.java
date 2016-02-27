package com.tabsontally.markomarks.tabsontally;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.tabsontally.markomarks.dbManager.BillDetailsDataManager;
import com.tabsontally.markomarks.dbManager.PersonItemDataManager;
import com.tabsontally.markomarks.model.APIConfig;

/**
 * Created by MarkoPhillipMarkovic on 2/26/2016.
 */
public class BaseActivity extends AppCompatActivity {

    public Context context;
    public APIConfig tabsApi;
    LocalBroadcastManager broadcastManager;
    Intent legislatorListIntent;

    BillDetailsDataManager billDetailsDataManager = new BillDetailsDataManager();
    PersonItemDataManager personItemDataManager = new PersonItemDataManager();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_legislators:
                legislatorListIntent = new Intent(context, LegislatorsListActivity.class);
                context.startActivity(legislatorListIntent);
                return true;
            case R.id.menu_about:
                Toast.makeText(context, "NOT YET IMPLEMENTED", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
