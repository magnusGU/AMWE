package com.example.amwe.Model;

import android.content.Intent;

import com.example.amwe.Model.Items.Item;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

class ItemMockRealization extends Item {

    public ItemMockRealization() {

    }
    public ItemMockRealization(String id,
            String title,
            double price,
            User seller,
            String description,
            String condition,
            String date,
            String bookImage) {

        this.id = id;
        this.title = title;
        this.price = price;
        this.seller = seller;
        this.description = description;
        this.condition = condition;
        this.date = date;
        this.bookImage = bookImage;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    protected Map<String, Object> MoreToMap() {
        return null;
    }

    @Override
    protected void setMoreIntent(Intent intent) {
        System.out.println("Something");
    }
}

public class ItemTest {
    Item itemMock;
    @Before
    public void beforeTests() {
        itemMock = new ItemMockRealization();
    }
    @Test
    public void testToString() {
    }

    @Test
    public void toMap_OnEmptyClass() {
        Map<String, Object> listingToMap = itemMock.toMap();
        assertEquals(listingToMap, new HashMap<String, Object>());
    }

    @Test
    public void toMap_OnFullClass() {
        itemMock = new ItemMockRealization( "id",
                 "title",
         1,
         new User("seller"),
         "description",
         "condition",
         "date",
         "bookImage");
        Map<String, Object> listingToMap = itemMock.toMap();

        assertTrue(listingToMap.containsKey("title"));
        assertTrue(listingToMap.containsKey("price"));
        assertTrue(listingToMap.containsKey("seller"));
        assertTrue(listingToMap.containsKey("description"));
        assertTrue(listingToMap.containsKey("condition"));
        assertTrue(listingToMap.containsKey("date"));
        assertTrue(listingToMap.containsKey("bookImage"));
    }
}