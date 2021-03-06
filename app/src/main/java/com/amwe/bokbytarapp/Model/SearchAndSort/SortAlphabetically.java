package com.amwe.bokbytarapp.Model.SearchAndSort;

import com.amwe.bokbytarapp.Model.Items.Item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Concrete strategy
 * Sort a list alphabetically.
 *
 * @author Magnus Andersson, William Hugo
 */
public class SortAlphabetically implements SortStrategy {
    final private List<Item> list;

    public SortAlphabetically(List<Item> list) {
        this.list = list;
    }

    /**
     * Sorts the current list alphabetically in ascending order.
     * Not case sensitive.
     */
    @Override
    public void sort() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return l1.getTitle().compareToIgnoreCase(l2.getTitle());
            }
        });
    }

    /**
     * Sorts the current list alphabetically in descending order.
     * Not case sensitive.
     */
    @Override
    public void sortReversed() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return l1.getTitle().compareToIgnoreCase(l2.getTitle());
            }
        }.reversed());
    }
}
