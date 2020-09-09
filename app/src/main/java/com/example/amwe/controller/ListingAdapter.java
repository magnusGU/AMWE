package com.example.amwe.controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.amwe.R;
import com.example.amwe.model.Database;
import com.example.amwe.model.Listing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/* This class is intended to work as an adapter that will make it possible to show listings on the searchPage as a list*/
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHold> {
    final private ArrayList <Listing> listingList;
    private Database db;

    public static class ViewHold extends ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;


        public ViewHold(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.listing_card_image);
            mTextView=itemView.findViewById(R.id.listing_card_date);
        }

    }

    public ListingAdapter(final ArrayList<Listing> listingList) {
        this.listingList = listingList;
        db = new Database();

        DatabaseReference listings = db.getListings();
        ValueEventListener listingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listingList.clear();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Log.d("HERE", item.getValue().toString());
                    listingList.add(item.getValue(Listing.class));
                }
                Log.d("HERE", listingList.toString());
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("HERE", "onCancelled");
            }
        };
        listings.addValueEventListener(listingsListener);

    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Temporary should have a reference to card
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_card,parent,false);

        ViewHold vh = new ViewHold(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Listing currentListing=listingList.get(position);
        holder.mImageView.setImageResource(currentListing.getBookImage());
        holder.mTextView.setText(currentListing.getTitle());
            }

    @Override
    public int getItemCount() {
        return listingList.size();
    }


}
