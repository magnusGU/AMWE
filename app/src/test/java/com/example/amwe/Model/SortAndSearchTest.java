package com.example.amwe.Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SortAndSearchTest {
    @Test
    public void isFilteredListCorrectSize()  {
        ArrayList<Item> listings = new ArrayList<>();


        Item listing1 = new Book("123ABC","a","b","c",3,"hej","1",200,"säljare","bra","3000");
        Item listing2 = new Book("123ABC","b","b","c",3,"hej","1",200,"säljare","bra","3000");
        listings.add(listing1);
        listings.add(listing2);

        SearchFunction searchFunction = new SearchFunction(listings);
        searchFunction.performFiltering("a");
        assertEquals(1,listings.size());
    }
    @Test
    public void isFilteredListCorrectSizeWhenEmpty() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();


        Item listing1 = new Book("123ABC","a","b","c",3,"hej","1",200,"säljare","bra","3000");
        Item listing2 = new Book("123ABC","b","b","c",3,"hej","1",200,"säljare","bra","3000");
        listings.add(listing1);
        listings.add(listing2);
        ArrayList<Item> listingsCopy = new ArrayList<>(listings);
        SearchFunction searchFunction = new SearchFunction(listings);
        searchFunction.performFiltering("");
        assertEquals(2,listings.size());
    }

    @Test
    public void isFilteredEmptyCorrect() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();

        Item listing1 = new Book("123ABC","a","b","c",3,"hej","1",200,"säljare","bra","3000");
        Item listing2 = new Book("123ABC","b","b","c",3,"hej","1",200,"säljare","bra","3000");
        listings.add(listing1);
        listings.add(listing2);

        SearchFunction searchFunction = new SearchFunction(listings);
        searchFunction.performFiltering("c");
        assertEquals(0,listings.size());
    }

    @Test
    public void isSortedByPriceRight() throws InterruptedException {
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
        expected.add(listing4);
        expected.add(listing2);
        expected.add(listing1);
        expected.add(listing3);

        SortFunction sort = new SortFunction(listings);
        sort.sortPrice();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

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

        SortFunction sort = new SortFunction(listings);
        sort.sortAlphabetically();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

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

        SortFunction sort = new SortFunction(listings);
        sort.sortPrice();
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
        SortFunction sort = new SortFunction(listings);
        sort.sortPrice();

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

        SortFunction sort = new SortFunction(listings);
        sort.sortPrice();
        sort.sortAlphabetically();

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
        expected.add(listing2);
        expected.add(listing1);
        expected.add(listing4);

        SortFunction sort = new SortFunction(listings);
        sort.sortPrice();
        sort.sortAlphabetically();

        assertEquals(Arrays.toString(expected.toArray()),Arrays.toString(listings.toArray()));
    }

    @Test
    public void isSortedEmptyListStillEmpty() throws InterruptedException {
        ArrayList<Item> listings = new ArrayList<>();

        SortFunction sort = new SortFunction(listings);
        sort.sortPrice();
        sort.sortAlphabetically();
        assertEquals(0,listings.size());
    }
}