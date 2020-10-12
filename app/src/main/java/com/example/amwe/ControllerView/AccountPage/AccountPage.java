package com.example.amwe.ControllerView.AccountPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.ControllerView.AddListing;
import com.example.amwe.ControllerView.SearchPage.ListingAdapter;
import com.example.amwe.R;
import com.example.amwe.Model.Database.Database;
import com.example.amwe.Model.Items.Item;

import java.util.ArrayList;


public class AccountPage extends Fragment {

    public AccountPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account_page, container, false);

        Button addListing = v.findViewById(R.id.account_page_add_listing_button);
        addListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddListing.class));
            }
        });

        ImageButton settingsButton = v.findViewById(R.id.account_page_settings_button);
        settingsButton.setOnClickListener(openSettings());

        final RecyclerView myListings = v.findViewById(R.id.MyListings);
        createList(myListings, "listings");

        final RecyclerView favourites = v.findViewById(R.id.Favourites);
        createList(favourites, "favourites");


        initUI(v);

        return v;
    }

    private View.OnClickListener openSettings() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Settings.class));
            }
        };
    }


    /**
     * Populates the RecyclerView with listings.
     *
     * @param recyclerView The RecyclerView that should be populated.
     * @param listName     The String that decides which method in database is called.
     */
    private void createList(RecyclerView recyclerView, String listName) {
        ArrayList<Item> myListings = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        ListingAdapter adapter = new ListingAdapter(myListings, listName);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    /**
     * Initializes the user-interface for AccountPage.
     *
     * @param v The view that should be Initialized.
     */
    private void initUI(View v) {
        TextView name = v.findViewById(R.id.account_page_name);
        ImageView profilePicture = v.findViewById(R.id.account_page_profile_picture);
        //Obviously temporary but works now as a test.
        name.setText(Database.getName());
    }

    @Override
    public void onStart() {
        super.onStart();
        final RecyclerView favourites = getView().findViewById(R.id.Favourites);
        createList(favourites, "favourites");
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}