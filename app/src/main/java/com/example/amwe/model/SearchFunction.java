package com.example.amwe.model;

import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class SearchFunction {

    private ArrayList<Listing> list;
    private ArrayList<Listing> originalList;

    public SearchFunction(ArrayList<Listing> listingList){
        this.list = listingList;
        this.originalList = new ArrayList<>(listingList);
    }


    public myFilter getFilter() {
        return searchFilter;
    }

    private myFilter searchFilter = new myFilter();
        //filters listings by the text written

        public class myFilter {
            public void performFiltering(CharSequence charSequence) {
                ArrayList<Listing> filteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(originalList);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    for (Listing l : originalList) {
                        if (l.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(l);
                        }
                    }
                }

                publishResults(filteredList);


            }

        }
        public void publishResults(ArrayList<Listing> filtered) {
            list.clear();
            list.addAll( filtered);
        }

}
