package com.example.amwe.Model.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.amwe.ControllerView.MessagePage.ChatRoomAdapter;
import com.example.amwe.ControllerView.SearchPage.ListingAdapter;
import com.example.amwe.Model.Items.Book;
import com.example.amwe.Model.Items.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * Static class for communicating with the firebase database
 *
 * @author Ali Alladin, Elias, Magnus Andersson, William Hugo
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
    static public DatabaseReference getDatabaseReference() {
        return database.getReference();
    }

    /**
     * @return the reference to the specific dataset "listings"
     */
    static public DatabaseReference getListings() {
        return getDatabaseReference().child("listings");
    }

    /**
     * @return the the databaseReference for user ID for the user who is currently logged in
     */
    static public DatabaseReference getRefCurrentUser() {
        return getDatabaseReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    /**
     * @return the String user ID for the user who is currently logged in
     */
    static public String getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
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

        final DatabaseReference currentUserListings = getRefCurrentUser().child("listings");
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
        Database.getRefCurrentUser().child("listings").child(id).removeValue();
    }

    /**
     * Add new user to the database, not creating an authorized user, but adding public information
     * to the database, like name
     *  @param uid  user id, unique
     * @param name of the user, matches displayName, not unique
     * @param base64Photo
     */
    static public void addUser(String uid, String name, String base64Photo) {
        database.getReference().child("users").child(uid).setValue(name);
        database.getReference().child("users").child(uid).child("userImage").setValue(base64Photo);
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
                    DatabaseReference favouriteListings = getRefCurrentUser().child(child);
                    favouriteListings.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (final DataSnapshot item2 : snapshot.getChildren()) {
                                final String bookId = item.getKey();
                                if (bookId.equals(item2.getKey()) && uniqueListing(bookId, bookListings)) {
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
    static private boolean uniqueListing(String itemID, List<Item> bookListings) {
        for (Item i: bookListings) {
            if (itemID.equals(i.getId())) {
                return false;
            }
        }
        return true;
    }

    /*static public void addChat(String sender, String receiver) {
        final List<String> sortList= new ArrayList<>();
        final DatabaseReference chats = getDatabaseReference().child("chat_room");
        sortList.add(sender);
        sortList.add(receiver);
        Collections.sort(sortList);

        chats.orderByChild("/" + sortList.get(0) + sortList.get(1) + "/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("TAG", "onDataChange: 1");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    /**
     * Method used for sending messages.
     *
     * @param text - Message that will be sent
     * @param sender - The User ID of the sender
     * @param receiver - The User ID of the receiver
     * @param timeStamp - The time of when the message is being sent
     */
    static public void useChat(String text, final String sender, final String receiver,String timeStamp) {
        List<String> sortList= new ArrayList<>();
        DatabaseReference db = getDatabaseReference();
        DatabaseReference chats = getDatabaseReference().child("chat_room");
        final String key = chats.push().getKey();

        //Message message = new Message (text, sender);
        Map<String, String> map = new HashMap<>();
        map.put("message", text);
        map.put("sender", sender);
        map.put("receiver", receiver);
        map.put("timeStamp",timeStamp);

        sortList.add(sender);
        sortList.add(receiver);
        Collections.sort(sortList);

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/chat_room/" + "/" + sortList.get(0) + sortList.get(1) + "/" + key, map);

        db.updateChildren(childUpdates);
    }

    static public void getChatRooms(final List<DataSnapshot> items, final ChatRoomAdapter chatRoomAdapter) {
        DatabaseReference databaseReference =Database.getDatabase().getReference("/chat_room/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot item:snapshot.getChildren()){
                    if (item.toString().contains(FirebaseAuth.getInstance().getUid())){
                        items.add(item);
                    }
                }
                chatRoomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}