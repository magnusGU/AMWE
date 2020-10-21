package com.example.amwe.ControllerView.MessagePage;

import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.Model.Database.Database;
import com.example.amwe.Model.Messaging.Cryptography;
import com.example.amwe.Model.Messaging.PrivateKey;
import com.example.amwe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.util.List;
import java.util.Timer;

/**
 * @author Elias Johansson, William Hugo.
 *
 * This class displays all the messages that exists inside a conversation between two users
 * as a list. It is needed to use the Android RecyclerView, which it also inherits from.
 * The messages look different depending on if it is sent or received by the current user.
 * To do this it uses two inner classes: ReceivedMessageHolder and SentMessageHolder to differentiate between
 * the two accordingly.
 */

public class MessageListAdapter extends RecyclerView.Adapter {
    private List<DataSnapshot> messages;
    private static final int MESSAGE_SENT = 1;
    private static final int MESSAGE_RECEIVED = 2;
    private Cryptography crypt;
    private String n;
    private String decryptingBigInt;
    private String encryptedMessage;


    public MessageListAdapter(List<DataSnapshot> messages) {
        this.messages = messages;
        crypt = new Cryptography();
    }

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


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataSnapshot message = (DataSnapshot) messages.get(position);
        message.child("message");

        switch (holder.getItemViewType()) {
            case MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        /*If messages author is current user, place it to the right otherwise place it to the left
         * */
        DataSnapshot message = messages.get(position);
        if ((message.child("sender").getValue()).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return MESSAGE_SENT;
        } else return MESSAGE_RECEIVED;
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(DataSnapshot message) {
             encryptedMessage = (String) message.child("message").getValue();
             decryptAndShowMessage(message,messageText);

            timeText.setText((String) message.child("timeStamp").getValue());


            // Format the stored timestamp into a readable String using method.

        }

    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);

            //nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            //profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(DataSnapshot message) {
            decryptAndShowMessage(message,messageText);
            timeText.setText((String) message.child("timeStamp").getValue());

            // Format the stored timestamp into a readable String using method.
            //timeText.setText(Utils.formatDateTime(message.getCreatedAt()));

            //nameText.setText(message.getAuthorId());

            // Insert the profile image from the URL into the ImageView.
            //  Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }

    private void decryptAndShowMessage(DataSnapshot message, final TextView messageText){
        encryptedMessage = (String) message.child("message").getValue();

        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + " " + decryptingBigInt);
        DatabaseReference dbr = Database.getPrivateKeyReference();
        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println("hämtar @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                n = (String) snapshot.child("n").getValue();
                decryptingBigInt = (String) snapshot.child("decryptingBigInt").getValue();
                BigInteger bigIntDecrypt = new BigInteger(decryptingBigInt);
                BigInteger bigIntN = new BigInteger(n);
                PrivateKey pk = new PrivateKey(bigIntDecrypt, bigIntN);
                //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + " " + encryptedMessage);
                byte[] decode = Base64.decode(encryptedMessage, Base64.DEFAULT);
                String decryptedMessage = crypt.decrypt(decode, pk);

                messageText.setText(decryptedMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

