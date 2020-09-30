package com.example.amwe.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.amwe.R;
import com.example.amwe.model.Book;
import com.example.amwe.model.Database;
import com.example.amwe.model.Item;
import com.example.amwe.model.SearchFunction;
import com.example.amwe.model.SortFunction;
import com.example.amwe.view.ListingPageActivity;

import java.text.DecimalFormat;
import java.util.List;

/* This class is intended to work as an adapter that will make it possible to show listings on the searchPage as a list*/
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHold>{
    private List<Item> bookListings;
    private SearchFunction search;
    private SortFunction sort;
    private Context context;

    public static class ViewHold extends ViewHolder {
        final private ImageView bookImage;
        private TextView textViewDate;
        private TextView textViewTitle;
        private TextView textViewPrice;
        private TextView textViewCondition;



        public ViewHold(@NonNull View itemView) {
            super(itemView);
            bookImage=itemView.findViewById(R.id.listing_card_image);
            textViewDate=itemView.findViewById(R.id.listing_card_date);
            textViewTitle=itemView.findViewById(R.id.listing_);
            textViewPrice = itemView.findViewById(R.id.listing_card_price);
            textViewCondition = itemView.findViewById(R.id.listing_card_condition);
        }

    }

    public ListingAdapter(final List<Item> bookListings, final String listName){
        this.bookListings = bookListings;
        //create database with listener that will update recyclerView
        if(listName.equals("currentListings")) {
            Database.addListingListener(bookListings, this);
        }
        else if (listName.equals("myListings")) {
            Database.addUserListener(bookListings, this);
        }
        else if (listName.equals("favourites")){
            Database.addFavouritesListener(bookListings, this);
        }
    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Temporary should have a reference to card
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_card,parent,false);
        this.context = parent.getContext();
        return new ViewHold(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHold holder, final int position) {
        final Book currentListing = (Book) bookListings.get(position);

        try{
            byte[] decodedString = Base64.decode(currentListing.getBookImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
              holder.bookImage.setImageBitmap(decodedByte);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.textViewTitle.setText(currentListing.getTitle());
        DecimalFormat df = new DecimalFormat("0.##");
        holder.textViewPrice.setText(df.format(currentListing.getPrice()) + " kr");
        holder.textViewCondition.setText(currentListing.getCondition());
        holder.textViewDate.setText(currentListing.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ListingPageActivity.class);
                currentListing.setIntent(intent);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookListings.size();
    }

    public SearchFunction getSearch(){
        if (search == null) {
            search = new SearchFunction(bookListings);
        }
        return search;
    }

    public SortFunction getSort(){
        if (sort == null) {
            sort = new SortFunction(bookListings);
        }
        return sort;
    }

}
