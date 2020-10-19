package com.example.amwe.ControllerView.MessagePage;

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

import com.example.amwe.Model.Database.Database;
import com.example.amwe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private TextView nameText;
    ImageView profileImage;
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

        final List <DataSnapshot> messageList = new ArrayList<>();
        /*Message m1 = new Message("Hejsan", Database.getCurrentUser().toString());
        Message m2 = new Message("Hallå där", "Kalle");
        messageList.add(m1);
        messageList.add(m2); */
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);

        nameText = findViewById(R.id.text_message_name);
        profileImage = findViewById(R.id.image_message_profile);

        mMessageRecycler.setHasFixedSize(true);
        mMessageAdapter = new MessageListAdapter(messageList);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.button_chatbox_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.edittext_chatbox);
                String messageText = editText.getText().toString();

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
                String dateString = df.format(new Date());



                if (!messageText.equals("")) {
                    Database.useChat(messageText, senderUid, receiverUid,dateString);
                }
                editText.setText("");
            }
        });
        DatabaseReference contact;
        if (senderUid.equals(FirebaseAuth.getInstance().getUid())){
        contact=Database.getDatabaseReference().child("users").child(receiverUid);
        }
        else {
            contact=Database.getDatabaseReference().child("users").child(senderUid);
        }
        contactName=findViewById(R.id.text_message_name);
        contactImage = findViewById(R.id.image_message_profile);

        contact.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactName.setText((String)snapshot.child("name").getValue());
                byte[] decodedString = Base64.decode((String) snapshot.child("userImage").getValue(), Base64.DEFAULT);

                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                contactImage.setImageBitmap(bitmap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        List<String> sortList= new ArrayList<>();
        sortList.add(senderUid);
        sortList.add(receiverUid);
        Collections.sort(sortList);
        DatabaseReference dbRef = Database.getDatabase().getReference("/chat_room/"+ "/" + sortList.get(0) + sortList.get(1) + "/");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot item : snapshot.getChildren()){
                    messageList.add(item);
                }
                mMessageAdapter.notify();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
