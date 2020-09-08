package com.example.amwe.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

import com.example.amwe.R;
import com.example.amwe.model.Listing;
import com.example.amwe.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<Listing> currentListings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Elias Test");
        createList();



    }
    /*Not the right place for it because of weird references to model but it will have to do for now*/
    private void createList(){
         this.currentListings= new ArrayList<>();
         currentListings.add(new Listing(1,"Testbok","Third","Testsson",123456789,"Denna bok är hårdkodad",null,300,new User()));
         currentListings.add(new Listing(2,"Testbok","Third","Testsson",123456789,"Denna bok är hårdkodad",null,400,new User()));
         currentListings.add(new Listing(3,"Testbok","Third","Testsson",123456789,"Denna bok är hårdkodad",null,500,new User()));
        CardView card =  findViewById(R.id.CardView);



    }
    private void initializeList(){


    }
}