package com.example.amwe.ControllerView.MessagePage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amwe.Model.Database.Database;
import com.example.amwe.Model.Messaging.Message;
import com.example.amwe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessagesPage extends Fragment {
    private  List <DataSnapshot> items = new ArrayList<>();
    private String receiver;
    private String sender;
    private String Message;
    private ChatRoomAdapter chatRoomAdapter;
    public MessagesPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {






        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_messages_page, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.message_Recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        getChatRooms();



        chatRoomAdapter = new ChatRoomAdapter(items,getContext());
        recyclerView.setAdapter(chatRoomAdapter);
        return v;
    }
    private void getChatRooms(){
        DatabaseReference databaseReference = Database.getDatabase().getReference("/chat_room/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    if (item.toString().contains(FirebaseAuth.getInstance().getUid())){
                         items.add(item);
                    }
                }
                chatRoomAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}