package com.tabsontally.markomarks.arrayadapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.tabsontally.markomarks.dbManager.PersonItemDataManager;
import com.tabsontally.markomarks.model.items.PersonItem;
import com.tabsontally.markomarks.tabsontally.R;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 2/25/2016.
 */
public class LegislatorListAdapter extends ArrayAdapter<PersonItem> implements Filterable {

    private Context ctx;

    PersonItemDataManager personItemDataManager = new PersonItemDataManager();
    ArrayList<PersonItem> mPersons;
    ArrayList<PersonItem> mAllPersons;
    ArrayList<PersonItem> mSavedPersons;


    public LegislatorListAdapter(Context context, ArrayList<PersonItem> allPersons, ArrayList<PersonItem> savedPersons) {
        super(context, 0, allPersons);
        ctx = context;
        mPersons = allPersons;
        mSavedPersons = savedPersons;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final PersonItem person = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.legislator_list_item, parent, false);
        }

        final CheckBox chbIsSelected = (CheckBox) convertView.findViewById(R.id.chb_legislator_is_selected);

        TextView txtLegislatorName = (TextView) convertView.findViewById(R.id.txt_legislator_list_name_item);

        ImageView legislatorImage = (ImageView) convertView.findViewById(R.id.img_legislator_list_image_item);

        LinearLayout lstLegislator = (LinearLayout) convertView.findViewById(R.id.lin_legislator);

        if (person != null) {
            chbIsSelected.setChecked(person.IsSelected);

            lstLegislator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mSavedPersons.contains(person)) {
                        Log.e("TABSONTALLY", "ADDING PERSON TO THE LIST " + person.FullName);
                        chbIsSelected.setChecked(true);
                        person.IsSelected = true;
                        mSavedPersons.add(person);
                    } else if (mSavedPersons.contains(person)) {
                        Log.e("TABSONTALLY", "----------REMOVING PERSON TO THE LIST " + person.FullName);
                        person.IsSelected = false;
                        mSavedPersons.remove(person);
                        chbIsSelected.setChecked(false);
                    }
                    personItemDataManager.savePersons(ctx, mSavedPersons);
                }
            });

            txtLegislatorName.setText(person.FullName);

            if (person.ImageUrl.length() > 0) {
                Ion.with(legislatorImage)
                        .placeholder(R.drawable.places_ic_clear)
                        .smartSize(false)
                        .load(person.ImageUrl);
            }
        }


        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                //mPersons = (ArrayList<PersonItem>) results.values;
                mPersons.clear();
                mPersons.addAll((ArrayList<PersonItem>) results.values); // has the filtered values
                Log.e("TABSONTALLY", "PUBLISHING " + String.valueOf(mPersons.size()));
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<PersonItem> FilteredArrList = new ArrayList<>();

                if (mAllPersons == null) {
                    mAllPersons = new ArrayList<>(mPersons); // saves the original data in mOriginalValues
                }
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mAllPersons.size();
                    results.values = mAllPersons;
                } else {
                    constraint = constraint.toString().toLowerCase();

                    for (int i = 0; i < mAllPersons.size(); i++) {
                        String data = mAllPersons.get(i).FullName;
                        if (data.toLowerCase().contains(constraint.toString())) {
                            Log.e("TABSONTALLY", "Data ->" + data);

                            FilteredArrList.add(mAllPersons.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}