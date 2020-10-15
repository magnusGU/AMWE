package com.example.amwe.ControllerView.MessagePage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.Model.Database.Database;
import com.example.amwe.Model.Messaging.Message;
import com.example.amwe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private TextView nameText;
    ImageView profileImage;
    private MessageListAdapter mMessageAdapter;
    private String sellerUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        Intent intent = getIntent();
        sellerUid = intent.getStringExtra("sellerUid");

        final List <Message> messageList = new ArrayList<>();
        Message m1 = new Message("Hejsan", Database.getCurrentUser().toString());
        Message m2 = new Message("Hallå där", "Kalle");
        messageList.add(m1);
        messageList.add(m2);
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);

        nameText = findViewById(R.id.text_message_name);
        nameText.setText(m2.getAuthorId());

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

                if (!messageText.equals("")) {
                    //Database.addChat(messageText, FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Database.useChat(messageText, FirebaseAuth.getInstance().getCurrentUser().getUid(), sellerUid);
                }
                /*
                Message newMessage = new Message(editText.getText().toString(), Database.getCurrentUser().toString());
                    //Kryptering, skicka upp till databasen här
                    messageList.add(newMessage);
                    editText.setText("");
                    mMessageAdapter.notifyDataSetChanged();
                 */
            }
        });

    }
}
