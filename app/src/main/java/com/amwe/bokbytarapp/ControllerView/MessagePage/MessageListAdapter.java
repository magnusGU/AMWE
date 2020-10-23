package com.amwe.bokbytarapp.ControllerView.MessagePage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amwe.bokbytarapp.Model.Database.Database;
import com.amwe.bokbytarapp.Model.Messaging.Cryptography;
import com.amwe.bokbytarapp.Model.Messaging.IMessage;
import com.amwe.bokbytarapp.Model.Messaging.IMessageFactory;
import com.amwe.bokbytarapp.Model.Messaging.PrivateKey;
import com.amwe.bokbytarapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Elias Johansson, William Hugo.
 *
 * Responsibilty: This class displays all the messages that exists inside a conversation between two users
 * as a list. It is needed to use the Android RecyclerView, which it also inherits from.
 * The messages look different depending on if it is sent or received by the current user.
 * To do this it uses two inner classes: ReceivedMessageHolder and SentMessageHolder to differentiate between
 * the two accordingly.
 * Used by: MessageListPage.
 * Uses: Database,Cryptography,IMessage,IMessageFactory,PrivateKey,SentMessageHolder,ReceivedMessageHolder
 */

public class MessageListAdapter extends RecyclerView.Adapter {
    private List<DataSnapshot> messages;
    private static final int MESSAGE_SENT = 1;
    private static final int MESSAGE_RECEIVED = 2;
    private Cryptography crypt;
    private String n;
    private String decryptingBigInt;

    /**
     * Constructor for the class.
     * @param messages a list of all the Strings that contain the messages of the current conversation.
     * Cryptography is instantiated here to be able to be accessed in listeners.
     *
     */
    public MessageListAdapter(List<DataSnapshot> messages) {
        this.messages = messages;
        crypt = new Cryptography();
    }

    /**
     * Creates a new GUI entity to populate the recyclerview depending on if the message is sent or received by
     * current user.
     *
     * @param parent Android class that is used to create a new card that holds the most recent message.
     * @param viewType is used to decide if the message is sent or received by sender.
     * @return either a view that represents a new received message or sent message.
     * @throws IllegalArgumentException if viewType is not defined.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == MESSAGE_SENT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sent_message, parent, false);
            return new SentMessageHolder(v);
        } else if (viewType == MESSAGE_RECEIVED) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_received_message, parent, false);
            return new ReceivedMessageHolder(v);
        }

        throw new IllegalArgumentException("ViewType is not defined. Message was not sent or received");
    }

    /**
     * Method to allocate message from the database to a matching ViewHolder.
     *
     * @param holder, the view that represents the message.
     * @param position, the index of the list.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataSnapshot message = messages.get(position);

        switch (holder.getItemViewType()) {
            case MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }

    }

    /**
     * Getter for the size of the list. Used in the background.
     *
     * @return the size of the list containing the messages.
     */
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * Compares the messages sender with current user. If they are equal, then the message has
     * been sent by user. Otherwise by the contact.
     *
     * @param position, index of the list.
     * @return an int to represent if the message has been sent or received by the user.
     */
    @Override
    public int getItemViewType(int position) {
        DataSnapshot message = messages.get(position);
        if ((message.child("sender").getValue()).equals(Database.getCurrentUser())) {
            return MESSAGE_SENT;
        } else return MESSAGE_RECEIVED;
    }

    /**
     * Private class that holds data and behaviour if message is sent by user.
     */

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText =  itemView.findViewById(R.id.text_message_body);
            timeText =  itemView.findViewById(R.id.text_message_time);
        }

       private void bind(DataSnapshot message) {
             decryptAndShowMessage(message,messageText);

            timeText.setText((String) message.child("timeStamp").getValue());
        }

    }

    /**
     * Private class that holds data and behaviour if message is received by user.
     */
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText =  itemView.findViewById(R.id.text_message_body);
            timeText =  itemView.findViewById(R.id.text_message_time);
        }

        private void bind(DataSnapshot message) {
            decryptAndShowMessage(message,messageText);

            timeText.setText((String) message.child("timeStamp").getValue());
        }
    }

    /**
     *
     * @param messageDb, message that will be decrypted and shown.
     * @param messageText, the TextView that will show the message.
     */
    private void decryptAndShowMessage(final DataSnapshot messageDb, final TextView messageText){

        DatabaseReference dbr = Database.getPrivateKeyReference();

        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n = (String) snapshot.child("n").getValue();
                decryptingBigInt = (String) snapshot.child("decryptingBigInt").getValue();
                BigInteger bigIntDecrypt = new BigInteger(decryptingBigInt);
                BigInteger bigIntN = new BigInteger(n);
                PrivateKey pk = new PrivateKey(bigIntDecrypt, bigIntN);

                String text = (String) messageDb.child("message").getValue();
                String senderId= (String) messageDb.child("sender").getValue();
                String receiverId= (String) messageDb.child("receiver").getValue();
                String timeStamp = (String) messageDb.child("timeStamp").getValue();



                IMessage message = IMessageFactory.createIMessage(text,senderId,receiverId,timeStamp);

                String decryptedMessage = message.decryptMessage(pk);

                messageText.setText(decryptedMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

