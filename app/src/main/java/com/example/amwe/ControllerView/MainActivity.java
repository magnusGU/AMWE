package com.example.amwe.ControllerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;

import com.example.amwe.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.pages, new SearchPage()).commit();

        BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.search_page:
                        selectedFragment = new SearchPage();
                        break;
                    case R.id.message_page:
                        selectedFragment = new MessagesPage();
                        break;
                    case R.id.account_page:
                        selectedFragment = new AccountPage();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.pages, selectedFragment).commit();
                return true;
            }
        };
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

}