package com.example.amwe.model;

import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

public class SearchFunction implements Filterable {

    private ArrayList<Listing> list;

    public SearchFunction(ArrayList<Listing> listingList){
        this.list = new ArrayList<>(listingList);
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        //filters listings by the text written

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Listing> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(list);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Listing l : list) {
                    if (l.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(l);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((ArrayList) filterResults.values);
        }
    };
}
