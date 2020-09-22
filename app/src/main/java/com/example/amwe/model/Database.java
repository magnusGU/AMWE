package com.example.amwe.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


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

    public void addUser(String uid, String name){
        User user = new User(name);
        database.getReference().child("users").child(uid).setValue(user);
    }
    
}