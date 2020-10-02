package com.example.amwe.Model;

import java.util.ArrayList;
import java.util.List;

public class Listing {
    private final List<Item> listings;

    public Listing() {
        listings = new ArrayList<>();
    }

    public void addItem(Item item) {
        listings.add(item);
    }

    public void removeItem(Item item) {
        listings.remove(item);
    }

}