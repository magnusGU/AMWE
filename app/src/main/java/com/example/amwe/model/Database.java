package com.example.amwe.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.amwe.controller.ListingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Database {
    static private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Database() {
        //database = FirebaseDatabase.getInstance();
    }


    static public FirebaseDatabase getDatabase() {
        return database;
    }

    static private DatabaseReference getDatabaseReference() {
        return database.getReference();
    }

    static public DatabaseReference getListings() {
        return getDatabaseReference().child("listings");
    }

    static public DatabaseReference getCurrentUser() {
        return getDatabaseReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    static public void updateListing(Item updatedListing) {
        DatabaseReference db = getDatabaseReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> updatedValues = updatedListing.toMap();

        String key = updatedListing.getId();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, updatedValues);
        childUpdates.put("/users/" + currentUid + "/listings/" + key, updatedValues);

        db.updateChildren(childUpdates);
    }

    static public void insertNewListing(Item newEntry) {
        DatabaseReference db = getDatabaseReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String displayName = getName();
        newEntry.setSeller(displayName);
        DatabaseReference listings = getListings();
        String key = listings.push().getKey();
        Map<String, Object> entryValues = newEntry.toMap();


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, entryValues);
        childUpdates.put("/users/" + currentUid + "/listings/" + key, entryValues);

        db.updateChildren(childUpdates);
    }

    static public void deleteListing(String id){
        Database.getListings().child(id).removeValue();
        Database.getCurrentUser().child("listings").child(id).removeValue();
    }

    static public void addUser(String uid, String name) {
        User user = new User(name);
        database.getReference().child("users").child(uid).setValue(user);
    }

    static public String getName() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }

    static public void addListingListener(final List<Item> bookListings,
                                          final ListingAdapter adapter) {
        DatabaseReference listings = getListings();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookListings.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    final String bookId = item.getKey();
                    final Book newListing = item.getValue(Book.class);
                    newListing.setId(bookId);
                    bookListings.add(newListing);
                }
                //Notify observers
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("HERE", "onCancelled");
            }
        };
        listings.addValueEventListener(listener);
    }

    static public void addUserListener(final List<Item> bookListings,
                                       final ListingAdapter adapter){

        final DatabaseReference currentUserListings = getCurrentUser().child("listings");
        ValueEventListener myListingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookListings.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                        final String bookId = item.getKey();
                        final Book newListing = item.getValue(Book.class);
                        newListing.setId(bookId);
                        bookListings.add(newListing);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        currentUserListings.addValueEventListener(myListingsListener);
    }

    static public void addFavouritesListener(final List<Item> bookListings,
                                             final ListingAdapter adapter){

        DatabaseReference allListings = getListings();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookListings.clear();
                for (final DataSnapshot item : snapshot.getChildren()) {
                    DatabaseReference favouriteListings = getCurrentUser().child("favourites");
                    favouriteListings.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (final DataSnapshot item2 : snapshot.getChildren()){
                                if (item.getKey().equals(item2.getKey())) {
                                    final String bookId = item.getKey();
                                    final Book newListing = item.getValue(Book.class);
                                    newListing.setId(bookId);
                                    bookListings.add(newListing);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                //Notify observers
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("HERE", "onCancelled");
            }
        };
        allListings.addValueEventListener(listener);
    }

}