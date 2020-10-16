package com.example.amwe.ControllerView.MessagePage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.Model.Database.Database;
import com.example.amwe.Model.Messaging.Message;
import com.example.amwe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private TextView nameText;
    ImageView profileImage;
    private MessageListAdapter mMessageAdapter;
    private String senderUid;
    private String receiverUid;

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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.edittext_chatbox);
                String messageText = editText.getText().toString();

                if (!messageText.equals("")) {
                    Database.useChat(messageText, senderUid, receiverUid);
                }
                editText.setText("");
            }
        });

        DatabaseReference dbRef = Database.getDatabase().getReference("/chat_room/"+ senderUid + "" + receiverUid);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    messageList.add(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
