package com.example.amwe.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.amwe.ControllerView.ListingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Static class for communicating with the firebase database
 */
public class Database {
    static private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    /**
     * private constructor,
     * Ensuring no one attempts to initialize class
     */
    private Database() {
        //database = FirebaseDatabase.getInstance();
    }

    /**
     * @return the entry point for accessing the firebase database
     */
    static public FirebaseDatabase getDatabase() {
        return database;
    }

    /**
     * @return an firebase reference which allows read and write to database
     */
    static private DatabaseReference getDatabaseReference() {
        return database.getReference();
    }

    /**
     * @return the reference to the specific dataset "listings"
     */
    static public DatabaseReference getListings() {
        return getDatabaseReference().child("listings");
    }

    /**
     * @return the user ID for the user who is currently logged in
     */
    static public DatabaseReference getCurrentUser() {
        return getDatabaseReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    /**
     * This method makes two updates in the database, one in the common listing dataset, and one
     * in the user-listings specific dataset, so all users can see the update to a listing, and so that
     * the user who posted the listing gets the updates
     *
     * @param updatedListing The item to be updated
     */
    static public void updateListing(Item updatedListing) {
        DatabaseReference db = getDatabaseReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> updatedValues = updatedListing.toMap();

        String key = updatedListing.getId();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, updatedValues);
        db.updateChildren(childUpdates);
    }

    /**
     * This method makes two inserts in the database, one in the common listing dataset, and one
     * in the user-listings specific dataset, so all users can see the new listing, and so that
     * the user who posted the listing gets an updates list of a listings posted by themselves
     *
     * @param newEntry The Item to be inserted in the database
     */
    static public void insertNewListing(Item newEntry) {

        DatabaseReference db = getDatabaseReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String displayName = getName();
        newEntry.setSeller(displayName);
        DatabaseReference listings = getListings();
        final String key = listings.push().getKey();
        Map<String, Object> entryValues = newEntry.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, entryValues);
        db.updateChildren(childUpdates);

        final DatabaseReference currentUserListings = getCurrentUser().child("listings");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUserListings.child(key).setValue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        currentUserListings.addValueEventListener(valueEventListener);
    }

    /**
     * Remove listing from database both from the common listings, and the user-listing specific entry
     *
     * @param id of the listing to be removed, identical in both places
     */
    static public void deleteListing(String id) {
        Database.getListings().child(id).removeValue();
        Database.getCurrentUser().child("listings").child(id).removeValue();
    }

    /**
     * Add new user to the database, not creating an authorized user, but adding public information
     * to the database, like name
     *
     * @param uid  user id, unique
     * @param name of the user, matches displayName, not unique
     */
    static public void addUser(String uid, String name) {
        User user = new User(name);
        database.getReference().child("users").child(uid).setValue(user);
    }

    /**
     * Returns the name of the user that is signed in.
     *
     * @return displayName of user, created when user was created
     */
    static public String getName() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }

    /**
     * Listener for the common listings dataset, will notify observers upon creation and
     * when dataset has changed
     *
     * @param bookListings List the observer want data pushed on
     * @param adapter      Observer to notify that there is new data
     */
    static public void addListingListener(final List<Item> bookListings, final ListingAdapter adapter) {
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

    /**
     * Listener for the current logged in user's own listings and favourites, will notify observers upon creation and
     * when dataset has changed
     *
     * @param bookListings The list the observer want data pushed on
     * @param adapter      Observer to notify that there is new data
     * @param child        Name of the child in the database that needs to be accessed.
     */
    static public void addUserListener(final List<Item> bookListings,
                                       final ListingAdapter adapter, final String child) {

        DatabaseReference allListings = getListings();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookListings.clear();
                for (final DataSnapshot item : snapshot.getChildren()) {
                    DatabaseReference favouriteListings = getCurrentUser().child(child);
                    favouriteListings.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (final DataSnapshot item2 : snapshot.getChildren()) {
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