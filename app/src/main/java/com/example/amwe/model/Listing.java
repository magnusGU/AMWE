package com.example.amwe.model;
import java.util.HashMap;
import java.util.Map;

public class Listing {

    private String id;
    private String title;
    private String edition;
    private String author;
    private long isbn;
    private String description;
    private String bookImage;
    private double price;
    private String condition;
    private String date;
    private User seller;

    public Listing (){
    }

    public Listing (String id, String title, String edition,String author,long isbn,String description,
                    String bookImage,double price,String seller,String condition, String date){
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

    public String getId() {
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

    public String getBookImage() {
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

    public void setId(String id) {
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

    public void setBookImage(String bookImage) {
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result;
        try {
            result = new HashMap<>();
            result.put("author", author);
            result.put("bookImage", bookImage);
            result.put("condition", condition);
            result.put("date", date);
            result.put("description", description);
            result.put("edition", edition);
            result.put("isbn", isbn);
            result.put("price", price);
            result.put("seller", seller.getName());
            result.put("title", title);
        } catch (NullPointerException e) {
            return new HashMap<>();
        }

        return result;
    }

}