package com.example.amwe.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
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

import com.example.amwe.R;
import com.example.amwe.model.Database;
import com.example.amwe.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

public class ListingPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.listing_page);
        initUI();
    }
    private void initUI(){
        try{
            ImageView bookImage = findViewById(R.id.listing_page_image);
            byte[] decodedString = Base64.decode(getIntent().getStringExtra("Image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            bookImage.setImageBitmap(decodedByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView title= findViewById(R.id.textView3);
        String titleName=getIntent().getStringExtra("Title");
        title.setText(titleName);
        TextView isbn = findViewById(R.id.listing_page_isbn);
        String newIsbn = getIntent().getStringExtra("isbn");
        isbn.setText(newIsbn);
        TextView description = findViewById(R.id.listing_page_description);
        String newDescription = getIntent().getStringExtra("description");
        description.setText(newDescription);
        TextView price = findViewById(R.id.listing_page_price);
        float newPrice = Float.parseFloat(getIntent().getStringExtra("price"));
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
        favourite.setOnClickListener(addToFavourites(bookId));


        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setVisibility(View.GONE);
        Button editButton = findViewById(R.id.edit_button);
        deleteButton.setVisibility(View.GONE);
    }

    private View.OnClickListener addToFavourites(final String bookId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Database.getCurrentUser().addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child("favourites").hasChild(bookId)){
                            Database.getCurrentUser().child("favourites").child(bookId).removeValue();
                        }
                        else {
                            Database.getCurrentUser().child("favourites").child(bookId).setValue(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        };
    }


}
