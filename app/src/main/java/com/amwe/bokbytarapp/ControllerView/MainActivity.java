package com.example.amwe.ControllerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;

import com.example.amwe.ControllerView.AccountPage.AccountPage;
import com.example.amwe.ControllerView.MessagePage.MessagesPage;
import com.example.amwe.ControllerView.SearchPage.SearchPage;
import com.example.amwe.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SearchPage searchPage = new SearchPage();
        final MessagesPage messagesPage = new MessagesPage();
        final AccountPage accountPage = new AccountPage();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.pages, searchPage).commit();

        BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.search_page:
                        selectedFragment = searchPage;
                        break;
                    case R.id.message_page:
                        selectedFragment = messagesPage;
                        break;
                    case R.id.account_page:
                        selectedFragment = accountPage;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.pages, selectedFragment).commit();
                return true;
            }
        };
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

}