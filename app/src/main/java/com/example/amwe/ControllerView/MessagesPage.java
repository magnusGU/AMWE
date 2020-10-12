package com.example.amwe.ControllerView;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amwe.Model.Message;
import com.example.amwe.R;

import java.util.ArrayList;
import java.util.List;

public class MessagesPage extends Fragment {

    public MessagesPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_messages_page, container, false);
        Message one = new Message("Första meddelandet","Test");
        Message two = new Message("Andra meddelandet","Test1");
        List testList = new ArrayList<Message>();
        testList.add(one);
        testList.add(two);
        RecyclerView recyclerView = v.findViewById(R.id.message_Recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



        MessageAdapter messageAdapter = new MessageAdapter(testList,getContext());
        recyclerView.setAdapter(messageAdapter);
        return v;
    }
}