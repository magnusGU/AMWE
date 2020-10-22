package com.example.amwe.Model.SearchAndSort;

import com.example.amwe.Model.Items.Item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Magnus Andersson, William Hugo
 * Concrete strategy
 * Sort a list alphabetically
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
