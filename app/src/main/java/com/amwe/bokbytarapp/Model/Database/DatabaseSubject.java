package com.amwe.bokbytarapp.Model.Database;

import androidx.annotation.NonNull;

import com.amwe.bokbytarapp.ControllerView.MessagePage.ChatRoomAdapter;
import com.amwe.bokbytarapp.ControllerView.SearchPage.ListingAdapter;
import com.amwe.bokbytarapp.Model.Items.Book;
import com.amwe.bokbytarapp.Model.Items.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Responsibility: This is the subject in the observer pattern. This will notify
 * observers about related changes to the database
 *
 * Used by: AccountPage, ChatRoomPage, SearchPage
 *
 * Uses: ChatRoomAdapter, ListingAdapter
 *
 * @author Magnus Andersson
 */
public class DatabaseSubject {
    static private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    /**
     * private constructor,
     * Ensuring no one attempts to initialize class
     */
    private DatabaseSubject() {
    }

    /**
     * Listener for the common listings dataset, will notify observers upon creation and
     * when dataset has changed
     *
     * @param bookListings List the observer want data pushed on
     * @param adapter      Observer to notify that there is new data
     */
    static public void addListingListener(final List<Item> bookListings, final ListingAdapter adapter) {
        DatabaseReference listings = database.getReference().child("listings");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookListings.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    final String bookId = item.getKey();
                    final Item newListing = item.getValue(Book.class);
                    newListing.setId(bookId);
                    bookListings.add(newListing);
                }
                //Notify observers
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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

        DatabaseReference allListings =  database.getReference().child("listings");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookListings.clear();
                for (final DataSnapshot item : snapshot.getChildren()) {
                    DatabaseReference favouriteListings = database
                            .getReference().child("users")
                            .child(FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getUid())
                            .child(child);
                    favouriteListings.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (final DataSnapshot item2 : snapshot.getChildren()) {
                                final String bookId = item.getKey();
                                if (bookId.equals(item2.getKey()) && uniqueListing(bookId, bookListings)) {
                                    final Item newListing = item.getValue(Book.class);
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
            }
        };
        allListings.addValueEventListener(listener);

    }

    /**
     * Private method for identifying a unique listing
     * @param itemID String to check if unique
     * @param bookListings List of items to check if contains unique
     * @return true of false
     */
    static private boolean uniqueListing(String itemID, List<Item> bookListings) {
        for (Item i : bookListings) {
            if (itemID.equals(i.getId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method used for getting chat rooms to be displayed in a list for the user
     *
     * @param items             - The list of chat rooms
     * @param chatRoomAdapter   - The adapter which makes the list viewable & interactable
     */
    static public void getChatRooms(final List<DataSnapshot> items, final ChatRoomAdapter chatRoomAdapter) {
        DatabaseReference databaseReference = Database.getDatabase().getReference("/chat_room/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (item.getKey().startsWith(FirebaseAuth.getInstance().getUid())) {
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
