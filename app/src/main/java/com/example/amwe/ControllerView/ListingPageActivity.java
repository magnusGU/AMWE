package com.example.amwe.ControllerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.amwe.EditListing;
import com.example.amwe.R;
import com.example.amwe.Model.Database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class ListingPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listing_page);
        initUI();
    }

    /**
     * Initializes the user interface.
     */
    private void initUI() {
        try {
            ImageView bookImage = findViewById(R.id.listing_page_image);
            byte[] decodedString = Base64.decode(getIntent().getStringExtra("Image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            bookImage.setImageBitmap(decodedByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView title = findViewById(R.id.textView3);
        String titleName = getIntent().getStringExtra("Title");
        title.setText(titleName);
        TextView isbn = findViewById(R.id.listing_page_isbn);
        long newIsbn = getIntent().getLongExtra("isbn", 0);
        isbn.setText(String.valueOf(newIsbn));
        TextView description = findViewById(R.id.listing_page_description);
        String newDescription = getIntent().getStringExtra("description");
        description.setText(newDescription);
        TextView price = findViewById(R.id.listing_page_price);
        double newPrice = getIntent().getDoubleExtra("price", 0);
        DecimalFormat df = new DecimalFormat("0.##");
        price.setText(df.format(newPrice) + " kr");
        TextView author = findViewById(R.id.listing_page_author);
        String newAuthor = getIntent().getStringExtra("author");
        author.setText(newAuthor);
        TextView edition = findViewById(R.id.listing_page_edition);
        String newEdition = getIntent().getStringExtra("edition");
        edition.setText(newEdition);
        TextView seller = findViewById(R.id.listing_page_seller);
        String newSeller = getIntent().getStringExtra("seller");
        seller.setText(newSeller);
        TextView condition = findViewById(R.id.listing_page_condition);
        String newCondition = getIntent().getStringExtra("condition");
        condition.setText(newCondition);

        String bookId = getIntent().getStringExtra("bookId");
        ImageButton favourite = findViewById(R.id.addToFavourites);
        changeIcon(bookId, favourite);
        favourite.setOnClickListener(addToFavourites(bookId, favourite));

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(deleteListing(bookId));

        Button editButton = findViewById(R.id.edit_button);
        Bundle bundle = createBundle(titleName, newEdition, newIsbn, newDescription, newAuthor, newCondition, newPrice, bookId, newSeller);
        editButton.setOnClickListener(editListing(bundle));


        CardView sellerInfo = findViewById(R.id.sellerInfo);
        TextView sellerLabel = findViewById(R.id.listing_page_seller_label);

        myListingView(bookId, favourite, deleteButton, editButton, sellerInfo, sellerLabel);

    }

    Bundle createBundle(String title, String edition, long isbn, String description, String author, String condition, double price, String bookId, String seller){
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("edition", edition);
        bundle.putLong("isbn", isbn);
        bundle.putString("description", description);
        bundle.putString("author", author);
        bundle.putString("condition", condition);
        bundle.putDouble("price", price);
        bundle.putString("bookId", bookId);
        bundle.putString("seller", seller);
        return bundle;
    }

    private View.OnClickListener editListing(final Bundle bundle) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListingPageActivity.this, EditListing.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        };
    }

    /**
     * Makes the a button call the deleteListing method in the Database class.
     *
     * @param bookId The id of the listing.
     * @return A View.OnClickListener that should be applied to the delete-button.
     */
    private View.OnClickListener deleteListing(final String bookId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database.deleteListing(bookId);
                finish();
            }
        };
    }


    /**
     * Method changes the view of ListingPage if the listing is CurrentUser's.
     *
     * @param bookId     The id of the listing.
     * @param favourite  The favourite-button.
     * @param delete     The delete-button.
     * @param edit       The edit-button
     * @param sellerCard The card with seller information.
     * @param label      The label over the card with seller information.
     */
    private void myListingView(final String bookId,
                               final ImageButton favourite,
                               final Button delete,
                               final Button edit,
                               final CardView sellerCard,
                               final TextView label) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("listings").hasChild(bookId)) {
                    favourite.setVisibility(View.GONE);
                    sellerCard.setVisibility(View.GONE);
                    label.setVisibility(View.GONE);
                    delete.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Database.getCurrentUser().addListenerForSingleValueEvent(listener);
    }


    /**
     * The method changes the icon of the button to a filled or outlined heart based on if it's added to favourites or not.
     *
     * @param favourite An imageButton whose icon want's to be changed.
     * @param bookId    The id of the listing.
     */
    private void changeIcon(final String bookId, final ImageButton favourite) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("favourites").hasChild(bookId)) {
                    favourite.setImageResource(R.drawable.ic_baseline_favorite_24);
                } else {
                    favourite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Database.getCurrentUser().addListenerForSingleValueEvent(listener);
    }


    /**
     * Adds the listing to favourites of current-user when button is pressed.
     *
     * @param bookId    The id of the listing.
     * @param favourite The imageButton whose icon want's to be changed when listing is added to favourites.
     * @return A View.OnClickListener that should be applied the AddToFavourites-button
     */
    private View.OnClickListener addToFavourites(final String bookId, final ImageButton favourite) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                ValueEventListener listener = new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("favourites").hasChild(bookId)) {
                            Database.getCurrentUser().child("favourites").child(bookId).removeValue();
                        } else {
                            Database.getCurrentUser().child("favourites").child(bookId).setValue(true);
                        }
                        changeIcon(bookId, favourite);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                Database.getCurrentUser().addListenerForSingleValueEvent(listener);
            }
        };
    }
}
