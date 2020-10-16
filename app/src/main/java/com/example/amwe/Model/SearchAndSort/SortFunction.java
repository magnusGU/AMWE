package com.example.amwe.Model.SearchAndSort;

import com.example.amwe.Model.Items.Item;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
     * Sorts the current list by price ascending order, from lowest to highest.
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
     * Sorts the current list by price descending order, from highest to lowest.
     */
    public void sortPriceReversed() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return Double.compare(l1.getPrice(), l2.getPrice());
            }
        }.reversed());
    }

    /**
     * Sorts the current list alphabetically in ascending order.
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
    /**
     * Sorts the current list alphabetically in descending order.
     * Not case sensitive.
     */
    public void sortAlphabeticallyReversed() {
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item l1, Item l2) {
                return l1.getTitle().compareToIgnoreCase(l2.getTitle());
            }
        }.reversed());
    }

    /**
     * Sorts the current list by date in ascending order.
     *
     */
    public void sortDate() {
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
     *
     */
    public void sortDateReversed() {
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
