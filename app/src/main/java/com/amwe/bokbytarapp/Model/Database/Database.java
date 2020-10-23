package com.amwe.bokbytarapp.Model.Database;

import androidx.annotation.NonNull;

import com.amwe.bokbytarapp.Model.Items.Item;
import com.amwe.bokbytarapp.Model.Messaging.CryptographyKeysCreator;
import com.amwe.bokbytarapp.Model.Messaging.IMessage;
import com.amwe.bokbytarapp.Model.Messaging.PrivateKey;
import com.amwe.bokbytarapp.Model.Messaging.PublicKey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsibility: Static class for communicating with the firebase database
 *
 * Used by: AddListing, EditListing, ListingPage, AccountPage, Settings, Register,
 * ChatRoomAdapter, MessageListAdapter, MessageListPage, ListingAdapter
 *
 * Uses: Item, CryptographyKeysCreator, IMessage,
 * PrivateKey, PublicKey
 *
 * @author Ali Alladin, Elias Johansson, Magnus Andersson, William Hugo
 */
public class Database {
    static private final FirebaseDatabase database = FirebaseDatabase.getInstance();


    /**
     * private constructor,
     * Ensuring no one attempts to initialize class
     */
    private Database() {
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
     *
     * @param uid         user id, unique
     * @param name        of the user, matches displayName, not unique
     * @param base64Photo
     */
    static public void addUser(String uid, String name, String base64Photo) {
        database.getReference().child("users").child(uid).child("name").setValue(name);
        database.getReference().child("users").child(uid).child("userImage").setValue(base64Photo);

        CryptographyKeysCreator createKey = new CryptographyKeysCreator();
        PublicKey publicKey = createKey.makePublicKey();
        PrivateKey privateKey = createKey.makePrivateKey();
        database.getReference().child("users").child(uid).child("public_key").child("n").setValue(publicKey.getN().toString());
        database.getReference().child("users").child(uid).child("public_key").child("encryptingBigInt").setValue(publicKey.getEncryptingBigInt().toString());
        database.getReference().child("users").child(uid).child("private_key").child("n").setValue(privateKey.getN().toString());
        database.getReference().child("users").child(uid).child("private_key").child("decryptingBigInt").setValue(privateKey.getDecryptingBigInt().toString());
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
     * Method used for sending messages.
     *
     * @param message, the message being sent to the database.
     */
    static public void useChat(final IMessage message) {
        final DatabaseReference db = getDatabaseReference();
        DatabaseReference chats = getDatabaseReference().child("chat_room");
        final String key = chats.push().getKey();


        //Creates keys for sender & receiver
        db.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BigInteger senderEncrypt = new BigInteger((String) snapshot.child(message.getSenderId()).child("public_key").child("encryptingBigInt").getValue());
                BigInteger senderN = new BigInteger((String) snapshot.child(message.getSenderId()).child("public_key").child("n").getValue());
                BigInteger receiverEncrypt = new BigInteger((String) snapshot.child(message.getReceiverId()).child("public_key").child("encryptingBigInt").getValue());
                BigInteger receiverN = new BigInteger((String) snapshot.child(message.getReceiverId()).child("public_key").child("n").getValue());
                PublicKey senderKey = new PublicKey(senderEncrypt,senderN);
                PublicKey receiverKey = new PublicKey(receiverEncrypt,receiverN);


                String base64SenderMessage = message.encryptMessage(senderKey);
                String base64ReceiverMessage= message.encryptMessage(receiverKey);

                Map<String, String> senderMap = new HashMap<>();
                senderMap.put("message", base64SenderMessage);
                senderMap.put("sender", message.getSenderId());
                senderMap.put("receiver", message.getReceiverId());
                senderMap.put("timeStamp", message.getTimeStamp());

                Map<String, String> receiverMap = new HashMap<>();
                receiverMap.put("message", base64ReceiverMessage);
                receiverMap.put("sender", message.getSenderId());
                receiverMap.put("receiver", message.getReceiverId());
                receiverMap.put("timeStamp", message.getTimeStamp());

                Map<String, Object> childUpdates = new HashMap<>();

                childUpdates.put("/chat_room/" + "/" + message.getSenderId() + message.getReceiverId() + "/" + key, senderMap);
                childUpdates.put("/chat_room/" + "/" + message.getReceiverId() + message.getSenderId() + "/" + key, receiverMap);

                db.updateChildren(childUpdates);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    static public DatabaseReference getPrivateKeyReference() {
        return Database.getDatabase().getReference("users").child(getCurrentUser()).child("private_key");
    }

    static public DatabaseReference getPublicKeyReference() {
        return Database.getDatabase().getReference("/users/").child(getCurrentUser()).child("/public_key/");
    }

}