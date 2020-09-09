package com.example.amwe.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.amwe.R;
import com.example.amwe.model.Listing;

import java.util.ArrayList;

/* This class is intended to work as an adapter that will make it possible to show listings on the searchPage as a list*/
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHold> {
    private ArrayList <Listing> listingList;
    private Context context;

    public static class ViewHold extends ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
        private View view;


        public ViewHold(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.listing_card_image);
            mTextView=itemView.findViewById(R.id.listing_card_date);
            this.view=itemView;
        }

    }
    public ListingAdapter(ArrayList<Listing> listingList) {
    this.listingList=listingList;


    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Temporary should have a reference to card
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_card,parent,false);
        this.context=parent.getContext();
        ViewHold vh = new ViewHold(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Listing currentListing=listingList.get(position);
        holder.mImageView.setImageResource(currentListing.getBookImage());
        holder.mTextView.setText(currentListing.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,ListingPageActivity.class));
            }
        });
            }

    @Override
    public int getItemCount() {
        return listingList.size();
    }


}
