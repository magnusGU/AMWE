package com.amwe.bokbytarapp.Model;

import android.content.Intent;

import com.amwe.bokbytarapp.Model.Items.Item;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
class ItemMockRealization extends Item {

    public ItemMockRealization() {

    }
    public ItemMockRealization(String id,
                               String title,
                               double price,
                               String seller,
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
    public void setIntent() {
        itemMock = new ItemMockRealization( "id",
                        "title",
                        1,
                        "seller",
                        "description",
                        "condition",
                        "date",
                        "bookImage");
        Intent intent = new Intent();
        itemMock.setIntent(intent);

        assertEquals(intent.getStringExtra("bookId"), "id");
        assertEquals(intent.getStringExtra("Title"), "title");
        assertEquals(intent.getDoubleExtra("price", 0), 1, 1);
        assertEquals(intent.getStringExtra("seller"), "seller");
        assertEquals(intent.getStringExtra("description"), "description");
        assertEquals(intent.getStringExtra("condition"), "condition");
        assertEquals(intent.getStringExtra("Image"), "bookImage");
    }
}