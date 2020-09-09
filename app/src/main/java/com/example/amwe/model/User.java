package com.example.amwe.model;

import java.util.ArrayList;

public class User {

    private String name;
    private ArrayList<Integer> watchlist;
    private ArrayList<Integer> favourites;

    public User(String name) {
        this.name = name;
    }
    public User(User newUser) {
        this.name = newUser.getName();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }



    public String getName() {
        return name;
    }
}
