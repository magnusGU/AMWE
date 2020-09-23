package com.example.amwe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortFunction {

    ArrayList<Listing> list;

    public SortFunction(ArrayList<Listing> list) {
        this.list = list;
    }

    private void sortPrice() {
        //TODO
    }

    private ArrayList<Listing> sortAlphabetically() {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Listing> updatedOrder = new ArrayList<>();

        for (Listing l:list) { //get all titles
            titles.add(l.getTitle());
        }

        Collections.sort(titles, new Comparator<String>() { //sorts list of titles alphabetically
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        for (String s: titles) { //sorts listings to be ordered like the list of titles
            for (Listing l:list) {
                if (l.getTitle().equals(s)) {
                    updatedOrder.add(l);
                    break;
                }
            }
        }

        return updatedOrder;
    }

}
