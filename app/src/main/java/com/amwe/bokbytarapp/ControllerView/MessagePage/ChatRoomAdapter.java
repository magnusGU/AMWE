package com.amwe.bokbytarapp.ControllerView.MessagePage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amwe.bokbytarapp.Model.Database.Database;
import com.amwe.bokbytarapp.Model.Messaging.IMessage;
import com.amwe.bokbytarapp.Model.Messaging.IMessageFactory;
import com.amwe.bokbytarapp.Model.Messaging.PrivateKey;
import com.amwe.bokbytarapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Elias Johansson
 * <p>
 * Responsibility:This class is an adapter that will show recent messages by Author in the RecyclerView.
 * It uses a List to hold all the conversations that pertains to the current user. It will then
 * look through that list for the most recent message in a conversation and show it.
 * Used by: ChatRoomsPage, DatabaseSubject.
 * Uses: MessageViewHold,Database,IMessage,IMessage,IMessageFactory,PrivateKey.
 */
public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MessageViewHold> {
    private final List<DataSnapshot> itemList;
    private Context context;
    private String contact;
    private String decryptingBigInt;
    private String n;

    public ChatRoomAdapter(List<DataSnapshot> messages, Context context) {
        this.itemList = messages;
        this.context = context;
    }

    /**
     * Class that holds all the information about a specific conversation and that is populating the RecyclerView.
     */
    public static class MessageViewHold extends RecyclerView.ViewHolder {
        private final TextView lastMessageText;
        private final TextView messageContact;
        private final ImageView messageProfile;
        private final TextView messageTimeStamp;
        private String contact;
        private String lastMessage;
        private boolean isLastSenderCurrentUser;
        private String timeStamp;


        /**
         * Constructor for ViewHold with all the different GUI elements.
         *
         * @param itemView Android class that assigns the MessageViewHold to the RecyclerView.
         */
        private MessageViewHold(@NonNull View itemView) {
            super(itemView);
            lastMessageText = itemView.findViewById(R.id.lastMessageText);
            messageContact = itemView.findViewById(R.id.messageContact);
            messageProfile = itemView.findViewById(R.id.message_Profile);
            messageTimeStamp = itemView.findViewById(R.id.message_timeStamp);

        }

    }

    /**
     * This method creates a new MessageViewHold that will then be populated in onBindViewHolder.
     *
     * @param parent   Android class that is used to create a new card that holds the most recent
     * @param viewType not used but needed if there are conditions for different types of views. Needed because of override.
     * @return a new MessageViewHold that then will be used to populate the recyclerview.
     */
    @NonNull
    @Override
    public MessageViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_card, parent, false);
        return new MessageViewHold(v);
    }

    /**
     * This method looks at the messages in a conversation and sends the latest with all information to the
     * MessageViewHold.
     *
     * @param holder   the object that will hold all the information about a conversation and then show it.
     * @param position an index to access the current DataSnapshot in itemList.
     */
    @Override
    public void onBindViewHolder(@NonNull final MessageViewHold holder, int position) {
        DataSnapshot item = itemList.get(position);
        List<DataSnapshot> messages = new ArrayList<>();
        for (DataSnapshot i : item.getChildren()) {
            messages.add(i);
        }
        DataSnapshot lastChat = messages.get(messages.size() - 1);


         String currentUserName = Database.getCurrentUser();
        if (lastChat.child("sender").getValue() != null && currentUserName != null) {


            if (lastChat.child("sender").getValue().equals(currentUserName)) {
                contact = (String) lastChat.child("receiver").getValue();
                holder.isLastSenderCurrentUser = true;
            } else {
                contact = (String) lastChat.child("sender").getValue();
                holder.isLastSenderCurrentUser = false;
            }
        }


        holder.lastMessage = (String) lastChat.child("message").getValue();
        holder.timeStamp = (String) lastChat.child("timeStamp").getValue();

        holder.contact = contact;
        DatabaseReference reference = Database.getDatabaseReference();
        DatabaseReference currentUserReference = Database.getPrivateKeyReference();
        currentUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                n = (String) snapshot.child("n").getValue();
                decryptingBigInt = (String) snapshot.child("decryptingBigInt").getValue();
                BigInteger bigIntDecrypt = new BigInteger(decryptingBigInt);
                BigInteger bigIntN = new BigInteger(n);
                PrivateKey pk = new PrivateKey(bigIntDecrypt, bigIntN);
                String senderId;
                String receiverId;
                if (holder.isLastSenderCurrentUser){
                    senderId = FirebaseAuth.getInstance().getUid();
                    receiverId = holder.contact;
                }
                else {
                    senderId = holder.contact;
                    receiverId = FirebaseAuth.getInstance().getUid();
                }

                IMessage message = IMessageFactory.createIMessage(holder.lastMessage,senderId,receiverId,holder.timeStamp);
                holder.lastMessage = message.decryptMessage(pk);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = reference.child("users").child(contact);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             *This listener is called once to access the contacts user data, such as name and message.
             *It is then showed by GUI with the help of the holder object.
             * @param snapshot, the user who is the contact of the current user.
             *
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String prefix;
                if (holder.isLastSenderCurrentUser) {
                    prefix = "Du: ";
                } else {
                    prefix = snapshot.child("name").getValue() + ":" + " ";
                }

                holder.messageContact.setText((String) snapshot.child("name").getValue());
                holder.messageTimeStamp.setText(holder.timeStamp);
                holder.lastMessageText.setText(prefix+holder.lastMessage);

                if (snapshot.child("userImage").getValue() != null) {
                    byte[] decodedString = Base64.decode((String) snapshot.child("userImage").getValue(), Base64.DEFAULT);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    holder.messageProfile.setImageBitmap(bitmap);
                } else {
                    Drawable d = context.getResources().getDrawable(R.drawable.ic_baseline_person_24);
                    holder.messageProfile.setImageDrawable(d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /**
         * Listener that binds one conversation to open the specific chat room for that conversation.
         * If clicked it will take the user to the common chat room of the contact and the current user.
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageListPage.class);
                intent.putExtra("sellerUid", holder.contact);

                context.startActivity(intent);
            }
        });

    }

    /**
     * @return the size of the list that holds all chat rooms that pertains to the current user.
     * It is used by background Android functionality to get the current index of the list.
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
