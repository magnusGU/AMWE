package com.example.amwe.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private FirebaseDatabase database;

    public Database() {
        this.database = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }
    public DatabaseReference getListings() {
        return database.getReference().child("listings");
    }
}