package com.example.amwe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortFunction {

    List<Listing> list;
    List<Listing> originalList;

    public SortFunction(List<Listing> list) {
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

    public List<Listing> getOriginalList() {
        return originalList;
    }
}
