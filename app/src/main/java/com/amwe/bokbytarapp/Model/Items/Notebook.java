package com.amwe.bokbytarapp.Model.Items;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Magnus Andersson
 *
 * This class represents handwritten notebooks by students,
 * so the only extension from the super class is which course the notebook was written for
 */
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
        this.seller = seller;
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
                ", seller='" + seller + '\'' +
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
