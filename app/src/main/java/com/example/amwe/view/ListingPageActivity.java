package com.example.amwe.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amwe.R;

public class ListingPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.listing_page);
        initUI();
    }
    private void initUI(){
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
        String newPrice = getIntent().getStringExtra("price");
        price.setText(newPrice);
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

    }
}
