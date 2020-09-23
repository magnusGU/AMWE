package com.example.amwe.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class Database {
    private FirebaseDatabase database;

    public Database() {
        this.database = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }
    public DatabaseReference getListings() {
        return database.getReference().child("listings");
    }

    public void addUser(String uid, String name){
        User user = new User(name);
        database.getReference().child("users").child(uid).setValue(user);
    }

    public String getName(String uid){
        final String[] name = new String[1];
        database.getReference().child("users").child(uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        name[0] = snapshot.getValue(User.class).getName();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
                );
        System.out.println(name[0]);
        return name[0];
    }
    
}