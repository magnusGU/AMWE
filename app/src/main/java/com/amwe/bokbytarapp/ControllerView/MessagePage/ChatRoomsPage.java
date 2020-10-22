package com.amwe.bokbytarapp.ControllerView.MessagePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amwe.bokbytarapp.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elias Johansson, Magnus Andersson
 * Class that instantiates the GUI for the messagesPage among them the RecyclerView
 * that will hold all the different conversations.
 */
public class ChatRoomsPage extends Fragment {
    private  List<DataSnapshot> items = new ArrayList<>();
    public ChatRoomsPage() {
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
        ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter(items, getContext());
        recyclerView.setAdapter(chatRoomAdapter);
        return v;
    }
}