package com.amwe.bokbytarapp.ControllerView.SearchPage;

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

import com.amwe.bokbytarapp.ControllerView.ListingPage;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortAlphabetically;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortByDate;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortByPrice;
import com.amwe.bokbytarapp.Model.SearchAndSort.SortStrategy;
import com.amwe.bokbytarapp.R;
import com.amwe.bokbytarapp.Model.Items.Item;
import com.amwe.bokbytarapp.Model.SearchAndSort.SearchFunction;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Responsibility:This class is intended to work as an adapter that will make it possible to show listings on the
 * searchPage as a list.
 * Used by: SearchPage, AccountPage, DatabaseSubject.
 * Uses: Item, SearchFunction,SortAlphabetically,SortByDate,SortByPrice,SortStrategy,ListingPage,ViewHold.
 *
 * @author Ali Alladin, Magnus Andersson,Elias Johansson
 */
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHold> {
    private final List<Item> bookListings;
    private SearchFunction search;
    private Context context;

    public static class ViewHold extends ViewHolder {
        final private ImageView bookImage;
        private final TextView textViewDate;
        private final TextView textViewTitle;
        private final TextView textViewPrice;
        private final TextView textViewCondition;

        /**
         * Constructor for ViewHold.
         *
         * @param itemView
         */
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.listing_card_image);
            textViewDate = itemView.findViewById(R.id.listing_card_date);
            textViewTitle = itemView.findViewById(R.id.listing_);
            textViewPrice = itemView.findViewById(R.id.listing_card_price);
            textViewCondition = itemView.findViewById(R.id.listing_card_condition);
        }

    }

    /**
     * Constructor for ListingAdapter.
     *
     * @param bookListings The list to which item's should be added.
     */
    public ListingAdapter(final List<Item> bookListings) {
        this.bookListings = bookListings;
    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Temporary should have a reference to card
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_card, parent, false);
        this.context = parent.getContext();
        return new ViewHold(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHold holder, final int position) {
        final Item currentListing = bookListings.get(position);

        try {
            byte[] decodedString = Base64.decode(currentListing.getBookImage(), Base64.DEFAULT);
            Bitmap srcBmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            Bitmap dstBmp;
            if (srcBmp.getWidth() >= srcBmp.getHeight()){

                dstBmp = Bitmap.createBitmap(
                        srcBmp,
                        srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                        0,
                        srcBmp.getHeight(),
                        srcBmp.getHeight()
                );

            }else{
                dstBmp = Bitmap.createBitmap(
                        srcBmp,
                        0,
                        srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                        srcBmp.getWidth(),
                        srcBmp.getWidth()
                );
            }

            holder.bookImage.setImageBitmap(dstBmp);

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
                Intent intent = new Intent(context, ListingPage.class);
                currentListing.setIntent(intent);
                context.startActivity(intent);
            }
        });
    }

    /**
     * A method that returns the size of the list bookListings.
     *
     * @return The size of bookListings.
     */
    @Override
    public int getItemCount() {
        return bookListings.size();
    }

    public SearchFunction getSearch() {
        if (search == null) {
            search = new SearchFunction(bookListings);
        }
        return search;
    }

    public SortStrategy sortMethod(int sortMethod) {
        switch (sortMethod) {
            case 1:
                return new SortAlphabetically(bookListings);
            case 2:
                return new SortByPrice(bookListings);
            case 3:
                return new SortByDate(bookListings);
        }
        throw new IndexOutOfBoundsException();
    }

}
