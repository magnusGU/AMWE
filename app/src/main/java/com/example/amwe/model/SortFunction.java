package com.example.amwe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortFunction {

    ArrayList<Item> list;
    ArrayList<Item> originalList;

    public SortFunction(ArrayList<Item> list) {
        this.list = list;
        this.originalList = new ArrayList<>(list);
    }

    public void sortPrice() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return Double.compare(l1.getPrice(),l2.getPrice());
            }
        });
    }

    public void sortAlphabetically() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return l1.getTitle().compareToIgnoreCase(l2.getTitle());
            }
        });
    }

    public ArrayList<Item> getOriginalList() {
        return originalList;
    }
}
