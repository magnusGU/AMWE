package com.example.amwe.model;

import android.media.Image;

public class Listing {
    private int id;
    private String title;
    private String edition;
    private String author;
    private int isbn;
    private String description;
    private Image bookImage;
    private double price;
    private User seller;

    public Listing (int id, String title, String edition,String author,int isbn,String description, Image bookImage,double price,User seller){
        this.id=id;
        this.title=title;
        this.edition=edition;
        this.author=author;
        this.isbn=isbn;
        this.description=description;
        this.bookImage=bookImage;
        this.price=price;
        this.seller=seller;


    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEdition() {
        return edition;
    }

    public String getAuthor() {
        return author;
    }

    public int getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public Image getBookImage() {
        return bookImage;
    }

    public double getPrice() {
        return price;
    }

    public User getSeller() {
        return seller;
    }
}
