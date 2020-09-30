package com.example.amwe.model;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BookTest {

    private Item bookTest;
    @Before
    public void beforeTests() {
        bookTest = new Book();
    }

    @Test
    public void testToString_returnsEmptyString() {
        assertEquals(bookTest.toString(),
                "Listing{id=null, title='null', edition='null', author='null', isbn=0, description='null', bookImage=null, price=0.0, seller=null}"
                );
    }

    @Test
    public void toMap_OnEmptyClass() {
        Map<String, Object> listingToMap = bookTest.toMap();
        assertEquals(listingToMap, new HashMap<String, Object>());
    }
    
    @Test
    public void toMap_OnFullClass() {
        bookTest = new Book("id",
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
        Map<String, Object> listingToMap = bookTest.toMap();

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