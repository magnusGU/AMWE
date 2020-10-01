package com.example.amwe.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private List<Integer> watchlist;
    private List<String> favourites;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "" + name + "";
    }


    public String getName() {
        return name;
    }
}
