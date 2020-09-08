package com.example.amwe.model;

import android.media.Image;

import java.util.List;

public class Listings {
    public int id;
    public String title;
    public String edition;
    public String author;
    public int isbn;
    public String description;
    public Image bookImage;
    public double price;

    private User seller;

    public Listings() {
    }

    public Listings(int id, String title, String edition, String author, int isbn,
                    String description, Image bookImage, double price, User seller) {

        this.id = id;
        this.title = title;
        this.edition = edition;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.bookImage = bookImage;
        this.price = price;
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Listings{" +
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
}
