package com.amwe.bokbytarapp.Model;

import com.amwe.bokbytarapp.Model.Items.Book;
import com.amwe.bokbytarapp.Model.Items.Item;
import com.amwe.bokbytarapp.Model.SearchAndSort.SearchFunction;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SearchTest {

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

}
