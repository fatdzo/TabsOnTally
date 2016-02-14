package com.tabsontally.markomarks.arrayadapters;

import android.content.Context;
import android.graphics.Color;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tabsontally.markomarks.model.minor.ContactDetail;
import com.tabsontally.markomarks.tabsontally.R;

import java.util.ArrayList;

/**
 * Created by MarkoPhillipMarkovic on 2/12/2016.
 */
public class ContactDetailsAdapter extends ArrayAdapter<ContactDetail> {
    Context ctx;
    TextView contactValue;
    TextView contactNote;
    TextView txt_contactType;

    public ContactDetailsAdapter(Context context, ArrayList<ContactDetail> contactDetails)
    {
        super(context, 0, contactDetails);
        ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ContactDetail contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contactinfo_item, parent, false);
        }

        // Lookup view for data population
        contactNote = (TextView) convertView.findViewById(R.id.txt_contactDetail_note);

        handleContactNoteGroupNames(position, contact);

        txt_contactType = (TextView) convertView.findViewById(R.id.txt_contactDetail_type);

        contactValue = (TextView) convertView.findViewById(R.id.txt_contactDetail_value);
        adjustContactTypeString(contact.getmType());
        setContactValueAutoLinkMask(contact.getmType());
        contactValue.setLinksClickable(true);
        contactValue.setClickable(true);

        contactValue.setText(contact.getmValue());

        return convertView;
    }

    public void handleContactNoteGroupNames(int position, ContactDetail contact)
    {
        if(position > 0)
        {
            ContactDetail previousContact = getItem(position - 1);
            if(!contact.getmNote().equals(previousContact.getmNote()))
            {
                contactNote.setText(contact.getmNote());
            }
            else
            {
                contactNote.setText("");
            }
        }
        else
        {
            contactNote.setText(contact.getmNote());
        }
    }


    public void adjustContactTypeString(String contactType)
    {
        txt_contactType.setText(contactType);

        if(contactType.equals("voice"))
        {
            txt_contactType.setText("phone");
        }

        if(contactType.equals("email"))
        {
            txt_contactType.setText("");
        }
    }

    public void setContactValueAutoLinkMask(String contactType) {
        if(contactType.equals("fax") || contactType.equals("voice")) {
            contactValue.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        }
        if(contactType.equals("address"))
        {
            contactValue.setAutoLinkMask(Linkify.MAP_ADDRESSES);
        }
        if(contactType.equals("email"))
        {
            contactValue.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
            contactNote.setText("email");
        }


    }

}
