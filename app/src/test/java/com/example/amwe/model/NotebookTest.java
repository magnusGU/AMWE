package com.example.amwe.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class NotebookTest {

    private Item notebookTest;
    @Before
    public void beforeTests() {
        notebookTest = new Notebook();
    }

    @Test
    public void testToString_returnsEmptyString() {
        assertEquals(notebookTest.toString(),
                "Listing{id=null, title='null', course='null', description='null', bookImage=null, price=0.0, condition=null, seller=null, date=null}"
        );
    }

    @Test
    public void toMap_OnEmptyClass() {
        Map<String, Object> listingToMap = notebookTest.toMap();
        assertEquals(listingToMap, new HashMap<String, Object>());
    }

    @Test
    public void toMap_OnFullClass() {
        notebookTest = new Notebook("id",
                "author",
                "course",
                "description",
                "bookImage",
                1,
                "seller",
                "condition",
                "date");
        Map<String, Object> listingToMap = notebookTest.toMap();

        assertTrue(listingToMap.containsKey("bookImage"));
        assertTrue(listingToMap.containsKey("condition"));
        assertTrue(listingToMap.containsKey("date"));
        assertTrue(listingToMap.containsKey("description"));
        assertTrue(listingToMap.containsKey("course"));
        assertTrue(listingToMap.containsKey("price"));
        assertTrue(listingToMap.containsKey("seller"));
        assertTrue(listingToMap.containsKey("title"));
    }
}