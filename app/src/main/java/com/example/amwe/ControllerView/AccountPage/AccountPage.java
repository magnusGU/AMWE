package com.example.amwe.ControllerView.AccountPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.ControllerView.AddListing;
import com.example.amwe.ControllerView.SearchPage.ListingAdapter;
import com.example.amwe.Model.Database.Database;
import com.example.amwe.Model.Items.Item;
import com.example.amwe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This class mainly makes sure that the signed in user's own published listings and saved listings
 * are presented in two lists on the Account Page. It's also a gateway to AddListing and Settings.
 * <p>
 * Related to {@link com.example.amwe.R.layout#fragment_account_page}.
 *
 * @author Ali Alladin
 */
public class AccountPage extends Fragment {
    RecyclerView myListings;

    public AccountPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account_page, container, false);

        initUI(v);

        Button addListing = v.findViewById(R.id.account_page_add_listing_button);
        addListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddListing.class));
            }
        });

        ImageButton settingsButton = v.findViewById(R.id.account_page_settings_button);
        settingsButton.setOnClickListener(openSettings());

        myListings = v.findViewById(R.id.MyListings);
        createList(myListings, "listings");

        final RecyclerView favourites = v.findViewById(R.id.Favourites);
        createList(favourites, "favourites");

        return v;
    }

    /**
     * Starts the Settings activity.
     *
     * @return A View.OnClickListener that should be applied on the settings-button.
     */
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
    private void initUI(final View v) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView name = v.findViewById(R.id.account_page_name);
                name.setText((String) snapshot.child("name").getValue());
                ImageView profilePicture = v.findViewById(R.id.account_page_profile_picture);

                try {
                    byte[] decodedString = Base64.decode((String) snapshot.child("userImage").getValue(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    profilePicture.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
      
        Database.getRefCurrentUser().addValueEventListener(valueEventListener);

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
        myListings.getAdapter().notifyDataSetChanged();

    }
}