package com.tabsontally.markomarks.model.items;

import com.tabsontally.markomarks.model.json.PersonDetails;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by MarkoPhillipMarkovic on 1/16/2016.
 */
public class PersonItem implements Serializable {
    public int Index;
    public String Id;
    public String FullName;
    public String ImageUrl;
    public PersonDetails Details;
    public boolean IsSelected = false;

    public static Comparator IsSelectedComparator = new Comparator<PersonItem>() {

        @Override
        public int compare(PersonItem e1, PersonItem e2) {
            boolean b1 = e1.IsSelected;
            boolean b2 = e2.IsSelected;
            if( b1 && ! b2 ) {
                return -1;
            }
            if( ! b1 && b2 ) {
                return +1;
            }
            return 0;
        }
    };


    public PersonItem()
    {
        Index = 0;
        Id = "none";
        FullName = "";
        ImageUrl = "";
    }

    @Override
    public boolean equals(Object another)
    {
        boolean areEqual = false;

        if (another != null && another instanceof PersonItem)
        {
            areEqual = this.Id.equals(((PersonItem) another).Id);
        }

        return areEqual;
    }
}

