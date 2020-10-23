package com.amwe.bokbytarapp.ControllerView.MessagePage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amwe.bokbytarapp.Model.Database.Database;
import com.amwe.bokbytarapp.Model.Messaging.IMessage;
import com.amwe.bokbytarapp.Model.Messaging.IMessageFactory;
import com.amwe.bokbytarapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Elias Johansson, William Hugo, Magnus Andersson.
 *
 * Resonsibility This class handles the creating of the GUI for the chatroom between two users and the list
 * of messages that their conversation holds.
 * Used by: ListingPage, ChatRoomAdapter.
 * Uses: Database, IMessage, ImessageFactory.
 */
public class MessageListPage extends AppCompatActivity {
    private MessageListAdapter mMessageAdapter;
    private String senderUid;
    private String receiverUid;
    private ImageView contactImage;
    private TextView contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        Intent intent = getIntent();
        receiverUid = intent.getStringExtra("sellerUid");
        senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final List<DataSnapshot> messageList = new ArrayList<>();
        RecyclerView mMessageRecycler = findViewById(R.id.reyclerview_message_list);

        mMessageRecycler.setHasFixedSize(true);
        mMessageAdapter = new MessageListAdapter(messageList);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.button_chatbox_send).setOnClickListener(sendMessage());
        DatabaseReference contact;

        if (senderUid.equals(FirebaseAuth.getInstance().getUid())) {
            contact = Database.getDatabaseReference().child("users").child(receiverUid);
        } else {
            contact = Database.getDatabaseReference().child("users").child(senderUid);
        }

        contactName = findViewById(R.id.text_message_name);
        contactImage = findViewById(R.id.image_message_profile);

        contact.addListenerForSingleValueEvent(getContactInfo());

        DatabaseReference dbRef = Database.getDatabase().getReference("/chat_room/" + "/" + senderUid + receiverUid + "/");

        dbRef.addValueEventListener(updateMessageList(messageList));
    }

    /**
     *
     * @return A onClicklistener which will send the written message when button is pressed
     */
    private View.OnClickListener sendMessage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.edittext_chatbox);
                String messageText = editText.getText().toString();

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
                String dateString = df.format(new Date());


                if (!messageText.equals("")) {

                    IMessage message = IMessageFactory.createIMessage(messageText,senderUid,receiverUid,dateString);

                    Database.useChat(message);
                }
                editText.setText("");
            }
        };
    }

    /**
     *
     * @return a ValueEventListener which will fetch and set name and user image
     * of the person you are conversing with from the database
     */
    private ValueEventListener getContactInfo() {
        return new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot){
                contactName.setText((String) snapshot.child("name").getValue());
                byte[] decodedString = Base64.decode((String) snapshot.child("userImage").getValue(), Base64.DEFAULT);

                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                contactImage.setImageBitmap(bitmap);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError error){

            }
        };
    }

    /**
     *
     * @param messageList The list of messages between the two parties to be updated
     * @return a ValueEventListener that will listen to the database entry between
     * the two parties and update the messageList as new messages are sent.
     */
    private ValueEventListener updateMessageList(final List<DataSnapshot> messageList) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    messageList.add(item);
                }
                mMessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
    }
}
