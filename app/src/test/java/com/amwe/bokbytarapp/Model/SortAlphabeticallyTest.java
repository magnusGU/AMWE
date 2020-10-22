package com.amwe.bokbytarapp.Model;

import com.amwe.bokbytarapp.Model.Items.Book;
import com.amwe.bokbytarapp.Model.Items.Item;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortAlphabetically;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortStrategy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SortAlphabeticallyTest {

    @Test
    public void isSortedAlphabeticallyRight() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();
        ArrayList<Item> expected = new ArrayList<>();

        Item listing1 = new Book("1","b","b","c",3,"hej","1",300,"säljare","bra","3000");
        Item listing2 = new Book("2","d","b","c",3,"hej","1",200,"säljare","bra","3000");
        Item listing3 = new Book("3","a","b","c",3,"hej","1",400,"säljare","bra","3000");
        Item listing4 = new Book("4","c","b","c",3,"hej","1",100,"säljare","bra","3000");
        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);
        listings.add(listing4);
        expected.add(listing3);
        expected.add(listing1);
        expected.add(listing4);
        expected.add(listing2);

        SortStrategy sort = new SortAlphabetically(listings);
        sort.sort();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

    @Test
    public void isSortedAlphabeticallyReverseRight() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();
        ArrayList<Item> expected = new ArrayList<>();

        Item listing1 = new Book("1","b","b","c",3,"hej","1",300,"säljare","bra","3000");
        Item listing2 = new Book("2","d","b","c",3,"hej","1",200,"säljare","bra","3000");
        Item listing3 = new Book("3","a","b","c",3,"hej","1",400,"säljare","bra","3000");
        Item listing4 = new Book("4","c","b","c",3,"hej","1",100,"säljare","bra","3000");
        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);
        listings.add(listing4);
        expected.add(listing2);
        expected.add(listing4);
        expected.add(listing1);
        expected.add(listing3);

        SortStrategy sort = new SortAlphabetically(listings);
        sort.sortReversed();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

}
