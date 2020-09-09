package com.example.amwe.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.MenuItem;

import com.example.amwe.R;
import com.example.amwe.controller.ListingAdapter;
import com.example.amwe.model.Listing;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    ArrayList<Listing> currentListings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(navListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.pages, new searchPage()).commit();
        createList();


    }
    /*Not the right place for it because of weird references to model but it will have to do for now*/
    public  void createList(){
        this.currentListings = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.RecycleView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ListingAdapter adapter = new ListingAdapter(currentListings);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initializeList() {

    }


    BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.search_page:
                    selectedFragment = new searchPage();
                    break;
                case R.id.message_page:
                    selectedFragment = new messagesPage();
                    break;
                case R.id.account_page:
                    selectedFragment = new accountPage();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.pages, selectedFragment).commit();
            return true;
        }
    };

}