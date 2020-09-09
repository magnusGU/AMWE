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


    }
}
