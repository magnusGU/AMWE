package com.example.amwe.view;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.amwe.R;
import com.example.amwe.model.Listing;

import java.util.ArrayList;

/* This class is intended to work as an adapter that will make it possible to show listings on the searchPage as a list*/
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHold> {
    private ArrayList <Listing> listingList;

    public static class ViewHold extends ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;


        public ViewHold(@NonNull View itemView) {
            super(itemView);
            //mImageView=itemView.findViewById()
            //mTextView=itemView.findViewById()
        }

    }
    public ListingAdapter(ArrayList<Listing> listingList) {
    this.listingList=listingList;

    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Temporary should have a reference to card
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_page,parent,false);
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

  /*  Context context;
    int resource;



    }*/

    /*Unfinished because there's no way to finish this method without working on fragments.*/
 /*   @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //We get our inflater
        LayoutInflater inflater= LayoutInflater.from(context);
        //inflates the resource (our xml)
        View view=inflater.inflate(resource,parent,false);
        /*
        If we want to update a Textview we would write the following code
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(myObject.getName());

         */


    //    return view;
    //  }*
}
