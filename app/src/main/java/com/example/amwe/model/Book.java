package com.example.amwe.model;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

public class Book extends Item {

    private String edition;
    private String author;
    private long isbn;

    public Book() {
    }

    public Book(String id, String title, String edition, String author, long isbn, String description,
                String bookImage, double price, String seller, String condition, String date) {
        this.id = id;
        this.title = title;
        this.edition = edition;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.bookImage = bookImage;
        this.price = price;
        this.condition = condition;
        this.date = date;
        this.seller = new User(seller);
    }


    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", edition='" + edition + '\'' +
                ", author='" + author + '\'' +
                ", isbn=" + isbn +
                ", description='" + description + '\'' +
                ", bookImage=" + bookImage +
                ", price=" + price +
                ", seller=" + seller +
                '}';
    }

    public String getEdition() {
        return edition;
    }

    public String getAuthor() {
        return author;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    @Override
    protected Map<String, Object> MoreToMap() {
        Map<String, Object> result;
        try {
            result = new HashMap<>();
            result.put("author", author);
            result.put("edition", edition);
            result.put("isbn", isbn);
        } catch (NullPointerException e) {
            return new HashMap<>();
        }
        return result;
    }

    @Override
    protected void setMoreIntent(Intent intent) {
        intent.putExtra("author", author);
        intent.putExtra("edition", edition);
        intent.putExtra("isbn", isbn);
    }
}