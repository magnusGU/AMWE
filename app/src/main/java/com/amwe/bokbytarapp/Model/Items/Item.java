package com.amwe.bokbytarapp.Model.Items;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Magnus
 *
 * This class is the abstract class that represent all the different items in the database.
 * Setters are needed because the database can not use the constructor for some reason.
 */

public abstract class Item {

    protected String id;
    protected String title;
    protected double price;
    protected String seller;
    protected String description;
    protected String condition;
    protected String date;
    protected String bookImage;

    public abstract String toString();

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }

    public String getDescription() {
        return description;
    }

    public String getCondition() {
        return condition;
    }

    public String getDate() {
        return date;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }


    //Template Method
    public Map<String, Object> toMap() {
        Map<String, Object> result;
        try {
            result = new HashMap<>();
            result.put("title", title);
            result.put("description", description);
            result.put("price", price);
            result.put("seller", seller);
            result.put("condition", condition);
            result.put("date", date);
            result.put("bookImage", bookImage);
            Map<String, Object> moreItems = MoreToMap();
            if (moreItems != null) {
                result.putAll(moreItems);
            }
        } catch (NullPointerException e) {
            return new HashMap<>();
        }

        return result;
    }

    protected abstract Map<String, Object> MoreToMap();

    //Template method
    public void setIntent(Intent intent) {

        intent.putExtra("bookId", id);
        intent.putExtra("Title", title);
        intent.putExtra("price", price);
        intent.putExtra("seller", seller);
        intent.putExtra("description", description);
        intent.putExtra("condition", condition);
        intent.putExtra("Image", bookImage);
        setMoreIntent(intent);
    }

    protected abstract void setMoreIntent(Intent intent);
}
