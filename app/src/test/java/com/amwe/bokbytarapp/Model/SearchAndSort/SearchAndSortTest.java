package com.amwe.bokbytarapp.Model.SearchAndSort;

import com.amwe.bokbytarapp.Model.Items.Book;
import com.amwe.bokbytarapp.Model.Items.Item;
import com.amwe.bokbytarapp.Model.SearchAndSort.SearchFunction;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortAlphabetically;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortByDate;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortByPrice;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortStrategy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SearchAndSortTest {

    @Test
    public void firstSearchThenSortCorrectly() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();
        ArrayList<Item> expected = new ArrayList<>();

        Item listing1 = new Book("1","aabb","b","c",3,"hej","1",300,"säljare","bra","3000");
        Item listing2 = new Book("2","abab","b","c",3,"hej","1",200,"säljare","bra","3000");
        Item listing3 = new Book("3","bbaa","b","c",3,"hej","1",600,"säljare","bra","3000");
        Item listing4 = new Book("4","baba","b","c",3,"hej","1",500,"säljare","bra","3000");
        Item listing5 = new Book("3","baab","b","c",3,"hej","1",400,"säljare","bra","3000");
        Item listing6 = new Book("4","abba","b","c",3,"hej","1",100,"säljare","bra","3000");

        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);
        listings.add(listing4);
        listings.add(listing5);
        listings.add(listing6);
        expected.add(listing1);
        expected.add(listing5);
        expected.add(listing3);

        SortStrategy sort = new SortByPrice(listings);
        sort.sort();
        SearchFunction searchFunction = new SearchFunction(listings);
        searchFunction.performFiltering("aa");

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

    @Test
    public void firstSortThenSearchCorrectly() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();
        ArrayList<Item> expected = new ArrayList<>();

        Item listing1 = new Book("1","aabb","b","c",3,"hej","1",300,"säljare","bra","3000");
        Item listing2 = new Book("2","abab","b","c",3,"hej","1",200,"säljare","bra","3000");
        Item listing3 = new Book("3","bbaa","b","c",3,"hej","1",600,"säljare","bra","3000");
        Item listing4 = new Book("4","baba","b","c",3,"hej","1",500,"säljare","bra","3000");
        Item listing5 = new Book("3","baab","b","c",3,"hej","1",400,"säljare","bra","3000");
        Item listing6 = new Book("4","abba","b","c",3,"hej","1",100,"säljare","bra","3000");

        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);
        listings.add(listing4);
        listings.add(listing5);
        listings.add(listing6);
        expected.add(listing1);
        expected.add(listing5);
        expected.add(listing3);

        SearchFunction searchFunction = new SearchFunction(listings);
        searchFunction.performFiltering("aa");
        SortStrategy sort = new SortByPrice(listings);
        sort.sort();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

    @Test
    public void sortPriceFirstThenAlphabeticallyRight() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();
        ArrayList<Item> expected = new ArrayList<>();

        Item listing1 = new Book("1","b","b","c",3,"hej","1",400,"säljare","bra","3000");
        Item listing2 = new Book("2","a","b","c",3,"hej","1",200,"säljare","bra","3000");
        Item listing3 = new Book("3","b","b","c",3,"hej","1",300,"säljare","bra","3000");
        Item listing4 = new Book("4","a","b","c",3,"hej","1",100,"säljare","bra","3000");
        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);
        listings.add(listing4);
        expected.add(listing4);
        expected.add(listing2);
        expected.add(listing3);
        expected.add(listing1);

        SortStrategy sort = new SortByPrice(listings);
        sort.sort();
        SortStrategy secondSort = new SortAlphabetically(listings);
        secondSort.sort();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

    @Test
    public void sortAlphabeticallyFirstThenPriceRight() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();
        ArrayList<Item> expected = new ArrayList<>();

        Item listing1 = new Book("1","b","b","c",3,"hej","1",200,"säljare","bra","3000");
        Item listing2 = new Book("2","a","b","c",3,"hej","1",400,"säljare","bra","3000");
        Item listing3 = new Book("3","a","b","c",3,"hej","1",100,"säljare","bra","3000");
        Item listing4 = new Book("4","b","b","c",3,"hej","1",300,"säljare","bra","3000");
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
        SortStrategy secondSort = new SortByPrice(listings);
        secondSort.sort();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

    @Test
    public void isSortedEmptyListStillEmpty() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();

        SortStrategy sort = new SortByPrice(listings);
        sort.sort();
        SortStrategy secondSort = new SortAlphabetically(listings);
        secondSort.sort();
        assertEquals(0,listings.size());
    }

}
