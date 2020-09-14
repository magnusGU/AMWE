package com.example.amwe.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.amwe.R;
import com.example.amwe.model.Database;
import com.example.amwe.model.Listing;
import com.example.amwe.view.ListingPageActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

/* This class is intended to work as an adapter that will make it possible to show listings on the searchPage as a list*/
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHold> implements Filterable{
    private ArrayList <Listing> listingList;
    private ArrayList<Listing> listingListCopy; //copy needed for search function
    private Context context;
    private Database db;

    public static class ViewHold extends ViewHolder {
        final private ImageView bookImage;
        private TextView textViewDate;
        private View view;
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
            this.view=itemView;
        }

    }
    public ListingAdapter(final ArrayList<Listing> listingList){
        this.listingList = listingList;
        //Simply an independent copy of listingList
        db = new Database();

        //create database listener that will update recyclerView
        //if the data in the database is changed
        DatabaseReference listings = db.getListings();
        ValueEventListener listingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listingList.clear();
                for (DataSnapshot item: snapshot.getChildren()) {
                    listingList.add(item.getValue(Listing.class));
                }
                //This line is what updated the view
                notifyDataSetChanged();
                listingListCopy = new ArrayList<>(listingList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("HERE", "onCancelled");
            }
        };
        //this listener is assigned to the databasereference
        listings.addValueEventListener(listingsListener);


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
    public void onBindViewHolder(@NonNull final ViewHold holder, final int position) {
        final Listing currentListing=listingList.get(position);

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
                intent.putExtra("Title", currentListing.getTitle());
                intent.putExtra("Image", currentListing.getBookImage());
                intent.putExtra("isbn", String.valueOf(currentListing.getIsbn()));
                intent.putExtra("description", currentListing.getDescription());
                intent.putExtra("price", String.valueOf(currentListing.getPrice()));
                intent.putExtra("author", currentListing.getAuthor());
                intent.putExtra("edition", currentListing.getEdition());
                intent.putExtra("condition", currentListing.getCondition());
                intent.putExtra("seller", currentListing.getSeller().toString());
                context.startActivity(intent);

            }
        });
            }

    @Override
    public int getItemCount() {
        return listingList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        //filters listings by the text written

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Listing> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listingListCopy);


            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Listing l : listingListCopy) {
                    if (l.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(l);


                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listingList.clear();
            listingList.addAll((ArrayList) filterResults.values);

            notifyDataSetChanged();
        }
    };
}
