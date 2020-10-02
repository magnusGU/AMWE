package com.example.amwe.Model;

public class User {

    private String name;

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
