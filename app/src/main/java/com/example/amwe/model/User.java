package com.example.amwe.model;

import java.util.ArrayList;

public class User{

    private String name;
    private ArrayList<Integer> watchlist;
    private ArrayList<Integer> favourites;

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
