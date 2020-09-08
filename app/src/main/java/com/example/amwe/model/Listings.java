package com.example.amwe.model;

import android.media.Image;

import java.util.List;

public class Listings {

    private int id;
    private String title;
    private String edition;
    private String author;
    private int isbn;
    private String description;
    private Image bookImage;
    private double price;
    private String seller; //Temporary string instead of User

    public Listings() {
    }

    public Listings(int id, String title, String edition, String author, int isbn,
                    String description, Image bookImage, double price, String seller) {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getBookImage() {
        return bookImage;
    }

    public void setBookImage(Image bookImage) {
        this.bookImage = bookImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

}
