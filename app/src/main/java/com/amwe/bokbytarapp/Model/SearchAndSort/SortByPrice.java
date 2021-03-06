package com.amwe.bokbytarapp.Model.SearchAndSort;

import com.amwe.bokbytarapp.Model.Items.Item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Concrete strategy
 * Sort a list by Price.
 *
 * Used by:
 * ListingAdapter.
 *
 * Uses:
 * SortStrategy.
 *
 * @author Magnus Andersson, William Hugo
 */
public class SortByPrice implements SortStrategy {

    final private List<Item> list;

    public SortByPrice(List<Item> list) {
        this.list = list;
    }

    /**
     * Sorts the current list by price ascending order, from lowest to highest.
     */
    @Override
    public void sort() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return Double.compare(l1.getPrice(), l2.getPrice());
            }
        });
    }
    /**
     * Sorts the current list by price descending order, from highest to lowest.
     */
    @Override
    public void sortReversed() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return Double.compare(l1.getPrice(), l2.getPrice());
            }
        }.reversed());
    }
}
