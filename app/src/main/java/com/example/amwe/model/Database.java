package com.example.amwe.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public DatabaseReference getMyListings() {
        return database.getReference().child("listings");
    }

    public void updateListing(Listing updatedListing) {
        DatabaseReference db = getDatabaseReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> updatedValues = updatedListing.toMap();

        String key = updatedListing.getId();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, updatedValues);
        childUpdates.put("/users/" + currentUid + "/" + key, updatedValues);

        db.updateChildren(childUpdates);
    }

    public void insertNewListing(Listing newEntry) {
        DatabaseReference db = getDatabaseReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String displayName = getName();
        newEntry.setSeller(displayName);
        DatabaseReference listings = getListings();
        String key = listings.push().getKey();
        Map<String, Object> entryValues = newEntry.toMap();


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, entryValues);
        childUpdates.put("/users/" + currentUid + "/" + key, entryValues);

        db.updateChildren(childUpdates);
    }

    public void addUser(String uid, String name){
        User user = new User(name);
        database.getReference().child("users").child(uid).setValue(user);
    }

    public String getName(){
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }
    
}