package com.example.amwe.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Database {
    private FirebaseDatabase database;

    public Database() {
        this.database = FirebaseDatabase.getInstance();
    }

    public ArrayList<Listings> getListings() {
        DatabaseReference listings = database.getReference().child("listings");
        final ArrayList<Listings> returnListings = new ArrayList<Listings>();
        ValueEventListener listingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                returnListings.clear();
                for (DataSnapshot item: snapshot.getChildren()) {
                    returnListings.add(item.getValue(Listings.class));
                }
                Log.d("HERE", returnListings.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("HERE", "onCancelled");
            }
        };

        listings.addValueEventListener(listingsListener);
        //System.out.println(listings);
        return null;
    }
}
