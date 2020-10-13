package com.example.amwe.Model.SearchAndSort;

import com.example.amwe.Model.Items.Book;
import com.example.amwe.Model.Items.Item;

import java.util.ArrayList;
import java.util.List;

public class SearchFunction {

    private final List<Item> list;
    private final List<Item> originalList;

    /**
     * Constructor
     *
     * @param listingList, the list of all items in the app.
     */
    public SearchFunction(List<Item> listingList) {
        this.list = listingList;
        this.originalList = new ArrayList<>(listingList);
    }

    //filters listings by a given CharSequence

    /**
     * @param charSequence, the sequence of chars that user puts into the searchbar.
     *                      The method looks for the title of the item and tries to match the charsequence with
     *                      it. An item has this sequence anywhere in its title, gets shown to the user.
     */
    public void performFiltering(CharSequence charSequence) {
        ArrayList<Item> filteredList = new ArrayList<>();

        if (charSequence == null || charSequence.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            String filterPattern = charSequence.toString().toLowerCase().trim();
            for (Item l : originalList) {
                if (l.getTitle().toLowerCase().contains(filterPattern)) {
                    filteredList.add(l);
                } else if (l.getClass()== Book.class) {
                    String s = String.valueOf(((Book) l).getIsbn());
                    if (s.contains(filterPattern)) {
                        filteredList.add(l);
                    }
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
