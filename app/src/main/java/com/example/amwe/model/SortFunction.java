package com.example.amwe.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A class that sorts the items in different ways.
 */
public class SortFunction {
    final List<Item> list;

    /**
     * Constructor
     *
     * @param list of type items that are currently shown to the user.
     */
    public SortFunction(List<Item> list) {
        this.list = list;
    }

    /**
     * Sorts the current list by price, from lowest to highest.
     */
    public void sortPrice() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return Double.compare(l1.getPrice(), l2.getPrice());
            }
        });
    }

    /**
     * Sorts the current list alphabetically.
     * Not case sensitive.
     */
    public void sortAlphabetically() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return l1.getTitle().compareToIgnoreCase(l2.getTitle());
            }
        });
    }

}
