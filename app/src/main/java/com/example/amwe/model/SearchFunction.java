package com.example.amwe.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchFunction {

    private List<Item> list;
    private List<Item> originalList;

    public SearchFunction(List<Item> listingList){
        this.list = listingList;
        this.originalList = new ArrayList<>(listingList);
    }

    //filters listings by a given CharSequence
    public void performFiltering(CharSequence charSequence) {
        ArrayList<Item> filteredList = new ArrayList<>();

        if (charSequence == null || charSequence.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            String filterPattern = charSequence.toString().toLowerCase().trim();
            for (Item l : originalList) {
                if (l.getTitle().toLowerCase().contains(filterPattern)) {
                    filteredList.add(l);
                }
            }
        }

        publishResults(filteredList);


    }


private void publishResults(List<Item> filtered) {
    list.clear();
    list.addAll(filtered);
}

}
