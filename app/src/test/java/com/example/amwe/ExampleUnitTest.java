package com.example.amwe;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.Filter;

import com.example.amwe.controller.ListingAdapter;
import com.example.amwe.model.Database;
import com.example.amwe.model.Listing;
import com.example.amwe.model.SearchFunction;
import com.example.amwe.model.SortFunction;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void isFilteredListCorrectSize() throws InterruptedException {
        ArrayList<Listing> listings = new ArrayList<>();


        Listing listing1 = new Listing("123ABC","a","b","c",3,"hej","1",200,"säljare","bra","3000");
        Listing listing2 = new Listing("123ABC","b","b","c",3,"hej","1",200,"säljare","bra","3000");
        listings.add(listing1);
        listings.add(listing2);
        ArrayList<Listing> listingsCopy = new ArrayList<>(listings);
        SearchFunction searchFunction = new SearchFunction(listings);
        searchFunction.performFiltering("a");
        assertEquals(1,listings.size());
    }

    @Test
    public void isFilteredEmptyCorrect() throws InterruptedException {
        ArrayList<Listing> listings = new ArrayList<>();


        Listing listing1 = new Listing("123ABC","a","b","c",3,"hej","1",200,"säljare","bra","3000");
        Listing listing2 = new Listing("123ABC","b","b","c",3,"hej","1",200,"säljare","bra","3000");
        listings.add(listing1);
        listings.add(listing2);
        ArrayList<Listing> listingsCopy = new ArrayList<>(listings);
        SearchFunction searchFunction = new SearchFunction(listings);
        searchFunction.performFiltering("c");
        assertEquals(0,listings.size());
    }

    @Test
    public void isSortedByPriceRight() throws InterruptedException {
        ArrayList<Listing> listings = new ArrayList<>();
        ArrayList<Listing> expected = new ArrayList<>();

        Listing listing1 = new Listing("1","b","b","c",3,"hej","1",300,"säljare","bra","3000");
        Listing listing2 = new Listing("2","d","b","c",3,"hej","1",200,"säljare","bra","3000");
        Listing listing3 = new Listing("3","a","b","c",3,"hej","1",400,"säljare","bra","3000");
        Listing listing4 = new Listing("4","c","b","c",3,"hej","1",100,"säljare","bra","3000");
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
        ArrayList<Listing> listings = new ArrayList<>();
        ArrayList<Listing> expected = new ArrayList<>();

        Listing listing1 = new Listing("1","b","b","c",3,"hej","1",300,"säljare","bra","3000");
        Listing listing2 = new Listing("2","d","b","c",3,"hej","1",200,"säljare","bra","3000");
        Listing listing3 = new Listing("3","a","b","c",3,"hej","1",400,"säljare","bra","3000");
        Listing listing4 = new Listing("4","c","b","c",3,"hej","1",100,"säljare","bra","3000");
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
        ArrayList<Listing> listings = new ArrayList<>();
        ArrayList<Listing> expected = new ArrayList<>();

        Listing listing1 = new Listing("1","aabb","b","c",3,"hej","1",300,"säljare","bra","3000");
        Listing listing2 = new Listing("2","abab","b","c",3,"hej","1",200,"säljare","bra","3000");
        Listing listing3 = new Listing("3","bbaa","b","c",3,"hej","1",600,"säljare","bra","3000");
        Listing listing4 = new Listing("4","baba","b","c",3,"hej","1",500,"säljare","bra","3000");
        Listing listing5 = new Listing("3","baab","b","c",3,"hej","1",400,"säljare","bra","3000");
        Listing listing6 = new Listing("4","abba","b","c",3,"hej","1",100,"säljare","bra","3000");
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
}