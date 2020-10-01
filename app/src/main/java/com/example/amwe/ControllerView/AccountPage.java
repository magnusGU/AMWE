package com.example.amwe.ControllerView;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amwe.R;
import com.example.amwe.model.Database;
import com.example.amwe.model.Item;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class AccountPage extends Fragment {

    public AccountPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_account_page, container, false);
        Button logOutButton = v.findViewById(R.id.account_logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        Button addListing = v.findViewById(R.id.account_page_add_listing_button);
        addListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddListing.class));
            }
        });



        final RecyclerView myListings = v.findViewById(R.id.MyListings);
        createList(myListings, "myListings");

        final RecyclerView favourites = v.findViewById(R.id.Favourites);
        createList(favourites, "favourites");


        initUI(v);

        return v;
        }


    /*Not the right place for it because of weird references to model but it will have to do for now*/
    private void createList(RecyclerView recyclerView, String listName){
        ArrayList<Item> myListings = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        //sketchy but we will have to discuss this
        ListingAdapter adapter = new ListingAdapter(myListings, listName);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void logOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), Login.class));
    }

    private void initUI(View v){
        TextView name =v.findViewById(R.id.account_page_name);
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
}