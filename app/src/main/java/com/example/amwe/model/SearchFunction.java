package com.example.amwe.model;

import java.util.ArrayList;
import java.util.List;

public class SearchFunction {

    private List<Listing> list;
    private List<Listing> originalList;

    public SearchFunction(List<Listing> listingList){
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
        public void publishResults(List<Listing> filtered) {
            list.clear();
            list.addAll(filtered);
        }

}
