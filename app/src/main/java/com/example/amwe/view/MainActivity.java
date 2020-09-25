package com.example.amwe.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.amwe.R;
import com.example.amwe.controller.ListingAdapter;
import com.example.amwe.model.Listing;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private ListingAdapter listingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //A SearchView might need to be created too
        final RecyclerView recyclerView = findViewById(R.id.RecycleView);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.pages, new SearchPage()).commit();

        createList(recyclerView);
        BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.search_page:
                        recyclerView.setVisibility(View.VISIBLE);
                        selectedFragment = new SearchPage();

                        break;
                    case R.id.message_page:
                        selectedFragment = new MessagesPage();
                        recyclerView.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.account_page:
                        recyclerView.setVisibility(View.INVISIBLE);
                        selectedFragment = new AccountPage();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.pages, selectedFragment).commit();
                return true;
            }
        };
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

    /*Not the right place for it because of weird references to model but it will have to do for now*/
    private void createList(RecyclerView recyclerView){
        ArrayList<Listing> currentListings = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //sketchy but we will have to discuss this
        ListingAdapter adapter = new ListingAdapter(currentListings, "currentListings");
        this.listingAdapter = adapter;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_function, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { //for updating the search after filling in text completely and submitting it in search bar, not used since we want it updated in real time
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) { //updating search in real time as the user writes
                listingAdapter.getSearch().performFiltering(s);
                try {
                    TimeUnit.MILLISECONDS.sleep(150); // OBS!!! Temporary! - This is to make sure that search list get updated before notifyDataSetChanged is called.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listingAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }
}