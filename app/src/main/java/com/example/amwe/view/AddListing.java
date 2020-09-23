package com.example.amwe.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amwe.R;
import com.example.amwe.model.Database;
import com.example.amwe.model.Listing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        Button mSubmit = findViewById(R.id.upload);
        EditText title = findViewById(R.id.input_title);
        EditText author = findViewById(R.id.input_author);
        EditText edition = findViewById(R.id.input_edition);
        EditText isbn = findViewById(R.id.input_ISBN);
        EditText description = findViewById(R.id.input_description);

        //TODO: Some blanks to fill in: image, price, condition

        mSubmit.setOnClickListener(submit(title, author, edition, isbn, description));
    }

    private View.OnClickListener submit(final EditText title, final EditText author, final EditText edition,
                                        final EditText isbn, final EditText description) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database();
                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                //Date date = new Date(System.currentTimeMillis());
                String dateString = dateFormat.format(new Date());

                Listing newBook = new Listing(null, title.getText().toString(),
                        edition.getText().toString(), author.getText().toString(),
                        Long.parseLong(isbn.getText().toString()), description.getText().toString(),
                        null, 0, null, null, dateString);


                db.insertNewListing(newBook);
                finish();
            }
        };
    }

}