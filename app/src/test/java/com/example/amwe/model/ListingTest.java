package com.example.amwe.model;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ListingTest {

    private Listing listingTest;
    @Before
    public void beforeTests() {
        listingTest = new Listing();
    }

    @Test
    public void testToString_returnsEmptyString() {
        assertEquals(listingTest.toString(),
                "Listing{id=null, title='null', edition='null', author='null', isbn=0, description='null', bookImage=null, price=0.0, seller=null}"
                );
    }

    @Test
    public void toMap_OnEmptyClass() {
        Map<String, Object> listingToMap = listingTest.toMap();
        assertEquals(listingToMap, new HashMap<String, Object>());
    }
    
    @Test
    public void toMap_OnFullClass() {
        listingTest = new Listing("id",
                "author",
                "edition",
                "author",
                1,
                "description",
                "bookImage",
                1,
                "seller",
                "condition",
                "date");
        Map<String, Object> listingToMap = listingTest.toMap();

        assertTrue(listingToMap.containsKey("author"));
        assertTrue(listingToMap.containsKey("bookImage"));
        assertTrue(listingToMap.containsKey("condition"));
        assertTrue(listingToMap.containsKey("date"));
        assertTrue(listingToMap.containsKey("description"));
        assertTrue(listingToMap.containsKey("edition"));
        assertTrue(listingToMap.containsKey("isbn"));
        assertTrue(listingToMap.containsKey("price"));
        assertTrue(listingToMap.containsKey("seller"));
        assertTrue(listingToMap.containsKey("title"));
    }
}