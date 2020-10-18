package com.example.amwe.ControllerView.MessagePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amwe.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Elias Johansson + ?
 * Class that instantiates the GUI for the messagesPage among them the RecyclerView
 * that will hold all the different conversations.
 */
public class MessagesPage extends Fragment {
    private  List<DataSnapshot> items = new ArrayList<>();
    //private String receiver;
    //private String sender;
    //private String Message;
    private ChatRoomAdapter chatRoomAdapter;
    public MessagesPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_messages_page, container, false);
        //Message one = new Message("Första meddelandet","Test");
        //Message two = new Message("Andra meddelandet","Test1");
        //List testList = new ArrayList<Message>();
        //testList.add(one);
        //testList.add(two);

        RecyclerView recyclerView = v.findViewById(R.id.message_Recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        chatRoomAdapter = new ChatRoomAdapter(items,getContext());
        recyclerView.setAdapter(chatRoomAdapter);
        return v;
    }
}