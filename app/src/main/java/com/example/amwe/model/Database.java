package com.example.amwe.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


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

    private DatabaseReference getDatabaseReference() {
        return database.getReference();
    }

    public DatabaseReference getListings() {
        return database.getReference().child("listings");
    }

    public void updateListing(Listing updatedListing) {
        DatabaseReference db = getDatabaseReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> updatedValues = updatedListing.toMap();

        String key = updatedListing.getId();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, updatedValues);
        childUpdates.put("/testusers/" + currentUid + "/" + key, updatedValues);

        db.updateChildren(childUpdates);
    }

    public void insertNewListing(Listing newEntry) {
        DatabaseReference db = getDatabaseReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference listings = getListings();
        String key = listings.push().getKey();
        Map<String, Object> entryValues = newEntry.toMap();

        String displayName = getName();
        newEntry.setSeller(displayName);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, entryValues);
        childUpdates.put("/testusers/" + currentUid + "/" + key, entryValues);

        db.updateChildren(childUpdates);
    }

    public void addUser(String uid, String name){
        User user = new User(name);
        database.getReference().child("users").child(uid).setValue(user);
    }

    public String getName(){
        String displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        //System.out.println(displayName);
        return displayName;
    }
    
}