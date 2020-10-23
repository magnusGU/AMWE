package com.amwe.bokbytarapp.ControllerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;

import com.amwe.bokbytarapp.ControllerView.AccountPage.AccountPage;
import com.amwe.bokbytarapp.ControllerView.MessagePage.ChatRoomsPage;
import com.amwe.bokbytarapp.ControllerView.SearchPage.SearchPage;
import com.amwe.bokbytarapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Responsibilities:
 * The activity you end up in after logging in.
 * It has a navigation bar which let you navigate the three fragments search_page, messages_page, and account_page.
 *
 * Used by:
 * Login.
 *
 * Uses:
 * SearchPage, MessagesPage, AccountPage.
 *
 * Related to {@link com.amwe.bokbytarapp.R.layout#activity_main}.
 *
 * @author Ali Alladin
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SearchPage searchPage = new SearchPage();
        final ChatRoomsPage chatRoomsPage = new ChatRoomsPage();
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
                        selectedFragment = chatRoomsPage;
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