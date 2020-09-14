package com.example.amwe.model;

import android.media.Image;

public class Listing {

    private int id;
    private String title;
    private String edition;
    private String author;
    private long isbn;
    private String description;
    private int bookImage;
    private double price;
    private String condition;
    private String date;
    private User seller;

    public Listing (){
    }

    public Listing (int id, String title, String edition,String author,long isbn,String description,
                    int bookImage,double price,String seller,String condition, String date){
        this.id=id;
        this.title=title;
        this.edition=edition;
        this.author=author;
        this.isbn=isbn;
        this.description=description;
        this.bookImage=bookImage;
        this.price=price;
        this.condition=condition;
        this.date=date;
        this.seller=new User(seller);
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

    public long getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public int getBookImage() {
        return bookImage;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public User getSeller() {
        return seller;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBookImage(int bookImage) {
        this.bookImage = bookImage;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public void setSeller(String seller) {
        this.seller = new User(seller);
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}