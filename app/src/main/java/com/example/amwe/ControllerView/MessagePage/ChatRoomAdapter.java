package com.example.amwe.ControllerView.MessagePage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.Model.Database.Database;
import com.example.amwe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * This class is intended as the adapter of the recycleview that will show recent messages by Author.
 * Sort of like Facebook Messenger
 */
public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MessageViewHold> {
    private final List itemList;
    private Context context;
    private String contact;
   // private DatabaseReference chatRoomReference;

    public ChatRoomAdapter(List<DataSnapshot> messages, Context context) {
        this.itemList = messages;
        this.context = context;

    }


    public static class MessageViewHold extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView lastMessageText;
        private final TextView messageContact;
        private String contact;


        /**
         * Constructor for ViewHold.
         *
         * @param itemView
         */
        public MessageViewHold(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.messageContact);
            lastMessageText=itemView.findViewById(R.id.lastMessageText);
            messageContact=itemView.findViewById(R.id.messageContact);
        }

    }


    @NonNull
    @Override
    public MessageViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_card, parent, false);
        return new MessageViewHold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHold holder, int position) {
        DataSnapshot item= (DataSnapshot) itemList.get(position);

        String receiver;


     /*   Query lastQuery=messages.

        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                holder.lastMessageText.setText((String)snapshot.child("message").getValue());
                if (snapshot.child("sender").getValue()!= FirebaseAuth.getInstance().getCurrentUser().getUid()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        for (DataSnapshot i:item.getChildren()) {
            DatabaseReference databaseReference = Database.getDatabase().getReference();
            if (i.child("sender").getValue()!=null&&FirebaseAuth.getInstance().getCurrentUser().getUid()!=null) {

                holder.lastMessageText.setText((String) i.child("message").getValue());

           if (i.child("sender").getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
               System.out.println("Detta är sändaren " + i.child("sender").getValue() + "Detta är mottagaren" + FirebaseAuth.getInstance().getCurrentUser().getUid());
               contact = (String) i.child("reciever").getValue();
               System.out.println("Sender" + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
               System.out.println(contact);
               System.out.println("Sender" + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

           }
            else{
               contact= (String) i.child("sender").getValue();
               System.out.println("Reciever" + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
               System.out.println(contact);
               System.out.println("Reciever" + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

            }}

        /*        if (i.child("reciever").getValue().equals(i.child("sender").getValue())){
                    throw new IllegalArgumentException("Database Error... Sender and reciever cant be the same person");
            }*/
        }
        holder.contact=contact;
        DatabaseReference reference = Database.getDatabaseReference();
        reference=reference.child("users").child(contact);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.messageContact.setText((String)snapshot.child("name").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Message message = (Message) itemList.get(position);
        //holder.textViewTitle.setText(message.getMessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageListActivity.class);
                System.out.println("Intent" + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.println(contact);
                System.out.println("Intent" + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

                intent.putExtra("sellerUid",holder.contact);

                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


}