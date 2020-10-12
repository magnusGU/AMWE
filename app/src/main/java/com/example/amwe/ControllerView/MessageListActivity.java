package com.example.amwe.ControllerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.Model.Database;
import com.example.amwe.Model.Message;
import com.example.amwe.R;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private TextView nameText;
    ImageView profileImage;
    private MessageListAdapter mMessageAdapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        List <Message> messageList = new ArrayList<>();
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

    }
}
