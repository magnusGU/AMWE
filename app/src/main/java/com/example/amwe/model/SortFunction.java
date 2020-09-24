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

    private ArrayList<Listing> sortPrice() {
        double[] prices = new double[list.size()];
        ArrayList<Listing> updatedOrder = new ArrayList();

        for (int i = 0; i < prices.length; i++) { //get all prices
            prices[i] = list.get(i).getPrice();
        }

        for (int i = 0; i < prices.length-1; i++) { //bubble-sorting, sorts
            for (int j = 0; j < prices.length-i-1; j++) {
                if (prices[j] > prices[j+1]) {
                    double temp = prices[j];
                    prices[j] = prices[j+1];
                    prices[j+1] = temp;
                }
            }
        }

        for (double d:prices) {
            for (Listing l:list) {
                if(l.getPrice() == d) {
                    updatedOrder.add(l);
                    break;
                }
            }
        }

        return updatedOrder;
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
