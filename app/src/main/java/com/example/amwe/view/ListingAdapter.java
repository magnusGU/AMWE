package com.example.amwe.view;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.amwe.model.Listing;

import java.util.ArrayList;

/* This class is intended to work as an adapter that will make it possible to show listings on the searchPage as a list*/
public class ListingAdapter extends ArrayAdapter<Listing> {
    Context context;
    int resource;

    public ListingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Listing> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource=resource;

    }

    /*Unfinished because there's no way to finish this method without working on fragments.*/
    @NonNull
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


        return view;
        }
}
