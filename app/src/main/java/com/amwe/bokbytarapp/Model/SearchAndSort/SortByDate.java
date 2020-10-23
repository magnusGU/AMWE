package com.amwe.bokbytarapp.Model.SearchAndSort;

import com.amwe.bokbytarapp.Model.Items.Item;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Concrete strategy
 * Sort a list by Date.
 *
 * Used by:
 * ListingAdapter.
 *
 * Uses:
 * SortStrategy.
 *
 * @author Magnus Andersson, William Hugo
 */
public class SortByDate implements SortStrategy {

    final private List<Item> list;

    public SortByDate(List<Item> list) {
        this.list = list;
    }

    /**
     * Sorts the current list by date in ascending order.
     */
    @Override
    public void sort() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                try {
                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                    Date date1 = dateFormat.parse(l1.getDate());
                    Date date2 = dateFormat.parse(l2.getDate());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
    /**
     * Sorts the current list by date in descending order.
     */
    @Override
    public void sortReversed() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                try {
                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                    Date date1 = dateFormat.parse(l1.getDate());
                    Date date2 = dateFormat.parse(l2.getDate());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        }.reversed());
    }
}
