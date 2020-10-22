package com.amwe.bokbytarapp.Model;

import com.amwe.bokbytarapp.Model.Items.Book;
import com.amwe.bokbytarapp.Model.Items.Item;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortByDate;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortStrategy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SortDateTest {

    @Test
    public void isSortedByDateRight() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();
        ArrayList<Item> expected = new ArrayList<>();

        Item listing1 = new Book("1","a","b","c",3,"hej","1",100,"säljare","bra", "20-Dec-2019");
        Item listing2 = new Book("2","a","b","c",3,"hej","1",100,"säljare","bra", "20-Sep-2020");
        Item listing3 = new Book("3","a","b","c",3,"hej","1",100,"säljare","bra", "19-Dec-2019");
        Item listing4 = new Book("4","a","b","c",3,"hej","1",100,"säljare","bra", "19-Sep-2020");
        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);
        listings.add(listing4);
        expected.add(listing3);
        expected.add(listing1);
        expected.add(listing4);
        expected.add(listing2);

        SortStrategy sort = new SortByDate(listings);
        sort.sort();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

    @Test
    public void isSortedByDateReverseRight() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();
        ArrayList<Item> expected = new ArrayList<>();

        Item listing1 = new Book("1","a","b","c",3,"hej","1",100,"säljare","bra", "20-Dec-2019");
        Item listing2 = new Book("2","a","b","c",3,"hej","1",100,"säljare","bra", "20-Sep-2020");
        Item listing3 = new Book("3","a","b","c",3,"hej","1",100,"säljare","bra", "19-Dec-2019");
        Item listing4 = new Book("4","a","b","c",3,"hej","1",100,"säljare","bra", "19-Sep-2020");
        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);
        listings.add(listing4);
        expected.add(listing2);
        expected.add(listing4);
        expected.add(listing1);
        expected.add(listing3);

        SortStrategy sort = new SortByDate(listings);
        sort.sortReversed();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

}
