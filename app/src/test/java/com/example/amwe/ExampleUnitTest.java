package com.example.amwe;
import com.example.amwe.model.Listing;
import com.example.amwe.model.SearchFunction;

import org.junit.Test;

import java.util.ArrayList;
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
        TimeUnit.SECONDS.sleep(1);
        assertEquals(1,listings.size());
    }
}