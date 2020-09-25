package com.example.amwe.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortFunction {

    ArrayList<Listing> list;
    ArrayList<Listing> originalList;

    public SortFunction(ArrayList<Listing> list) {
        this.list = list;
        this.originalList = new ArrayList<Listing>(list);
    }

    public void sortPrice() {
        Collections.sort(list, new Comparator<Listing>() {
            @Override
            public int compare(Listing l1, Listing l2) {
                return Double.compare(l1.getPrice(),l2.getPrice());
            }
        });
    }

    public void sortAlphabetically() {
        Collections.sort(list, new Comparator<Listing>() {
            @Override
            public int compare(Listing l1, Listing l2) {
                return l1.getTitle().compareToIgnoreCase(l2.getTitle());
            }
        });
    }

    public ArrayList<Listing> getOriginalList() {
        return originalList;
    }
}
