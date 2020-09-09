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

    public FirebaseDatabase getDatabase() {
        return database;
    }
    public DatabaseReference getListings() {
        return database.getReference().child("listings");
    }
    /*public ArrayList<Listing> getListings() {
        DatabaseReference listings = database.getReference().child("listings");
        final ArrayList<Listing> returnListings = new ArrayList<Listing>();
        ValueEventListener listingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                returnListings.clear();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Log.d("HERE", item.getValue().toString());
                    returnListings.add(item.getValue(Listing.class));
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
        return returnListings;
    }*/
}
