package com.example.amwe.Model.Items;

import android.content.Intent;

import com.example.amwe.Model.Items.Item;
import com.example.amwe.Model.User;

import java.util.HashMap;
import java.util.Map;

public class Notebook extends Item {

    private String course;

    public Notebook() {

    }

    public Notebook(String id,
                    String title,
                    String course,
                    String description,
                    String bookImage,
                    double price,
                    String seller,
                    String condition,
                    String date) {
        this.id = id;
        this.title = title;
        this.course = course;
        this.description = description;
        this.bookImage = bookImage;
        this.price = price;
        this.seller = new User(seller);
        this.condition = condition;
        this.date = date;


    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", course='" + course + '\'' +
                ", description='" + description + '\'' +
                ", bookImage=" + bookImage +
                ", price=" + price +
                ", condition=" + condition +
                ", seller=" + seller +
                ", date=" + date +
                '}';
    }

    @Override
    protected Map<String, Object> MoreToMap() {
        Map<String, Object> result;
        try {
            result = new HashMap<>();
            result.put("course", course);
        } catch (NullPointerException e) {
            return new HashMap<>();
        }
        return result;
    }

    @Override
    protected void setMoreIntent(Intent intent) {
        intent.putExtra("course", course);
    }
}
