package com.example.amwe.ControllerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amwe.R;
import com.example.amwe.Model.Items.Book;
import com.example.amwe.Utils.CameraInitializer;

import com.example.amwe.Model.Database.Database;
import com.example.amwe.Model.Items.Book;
import com.example.amwe.Model.Items.Item;
import com.example.amwe.R;
import com.example.amwe.Utilis.CameraInitializer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * AddListing handles creation and submission of a new listing to the database.
 * <p>
 * Related to {@link com.example.amwe.R.layout#activity_add_listing}.
 *
 * @author Ali Alladin
 */
public class AddListing extends AppCompatActivity {
    private ImageButton cameraClick;
    private final int CAMERA_PIC_REQUEST = 2;
    private String photoPath;
    private File photoFile;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
        cameraClick = findViewById(R.id.upload_image);
        Button mSubmit = findViewById(R.id.upload);
        EditText title = findViewById(R.id.input_title);
        EditText author = findViewById(R.id.input_author);
        EditText edition = findViewById(R.id.input_edition);
        EditText isbn = findViewById(R.id.input_ISBN);
        EditText description = findViewById(R.id.input_description);
        EditText price = findViewById(R.id.input_price);

        List<String> bookConditions = new ArrayList<>();
        bookConditions.add("Nyskick");
        bookConditions.add("Använd");
        bookConditions.add("Sliten");
        Spinner condition = findViewById(R.id.edit_condition);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookConditions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condition.setAdapter(adapter);


        //TODO: Some blanks to fill in: image, price, condition

        mSubmit.setOnClickListener(submit(title, author, edition, isbn, description, price, condition));

        cameraClick.setOnClickListener(camera());
    }


    /**
     * This method is called automatically when the camera activity is done. It converts the file stored into a compressed and viewable image.
     *
     * @param requestCode A code that differentiates between different possible actions taken when method is called.
     * @param resultCode  A code that shows the result of the operation.
     * @param data        Android functionality that transfers data between activities.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (photoURI != null) {
                File image = new File(photoFile.toURI());

                Bitmap srcBmp = BitmapFactory.decodeFile(image.getAbsolutePath());

                Bitmap dstBmp;
                //This is to handle if the camera app is closed before photo is taken
                if (srcBmp != null) {
                    if (srcBmp.getWidth() >= srcBmp.getHeight()) {

                        dstBmp = Bitmap.createBitmap(
                                srcBmp,
                                srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                                0,
                                srcBmp.getHeight(),
                                srcBmp.getHeight()
                        );

                    } else {
                        dstBmp = Bitmap.createBitmap(
                                srcBmp,
                                0,
                                srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                                srcBmp.getWidth(),
                                srcBmp.getWidth()
                        );
                    }

                    //Cast because of return type Object
                    //  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    cameraClick.setImageBitmap(dstBmp);
                    ViewGroup.LayoutParams params = cameraClick.getLayoutParams();
                    params.height = 1500;
                    cameraClick.setLayoutParams(params);
                }
            }
        }
    }

    private View.OnClickListener camera() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //Uri outputFileUri = Uri.fromFile(new File(getExternalCacheDir().getPath(), "pickImageResult.jpeg"));
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                CameraInitializer initializer = new CameraInitializer(getApplicationContext(), getExternalFilesDir(MediaStore.Images.ImageColumns.RELATIVE_PATH));
                try {
                    photoFile = initializer.createPhotoFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    photoURI = initializer.getURI();
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                }
            }
        };
    }


    /**
     * Adds the listing when button is pressed.
     *
     * @param title       The title of the listing.
     * @param author      The author of the book.
     * @param edition     The edition of the book.
     * @param isbn        The ISBN of the book
     * @param description The description for the listing.
     * @param price       The price of the listing.
     * @param condition   The condition of the book.
     * @return A View.OnClickListener that should be applied to the submit-button.
     */
    private View.OnClickListener submit(final EditText title,
                                        final EditText author,
                                        final EditText edition,
                                        final EditText isbn,
                                        final EditText description,
                                        final EditText price,
                                        final Spinner condition) {

        System.out.println(condition.getSelectedItem());

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (conditions(title, author, edition, isbn, price)) {

                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                    String dateString = dateFormat.format(new Date());

                    String base64Photo;
                    try {
                        Bitmap srcBmp = MediaStore.Images.Media.getBitmap(
                                getApplicationContext().getContentResolver(),
                                photoURI);

                        Bitmap dstBmp;
                        if (srcBmp.getWidth() >= srcBmp.getHeight()) {

                            dstBmp = Bitmap.createBitmap(
                                    srcBmp,
                                    srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                                    0,
                                    srcBmp.getHeight(),
                                    srcBmp.getHeight()
                            );

                        } else {
                            dstBmp = Bitmap.createBitmap(
                                    srcBmp,
                                    0,
                                    srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                                    srcBmp.getWidth(),
                                    srcBmp.getWidth()
                            );
                        }

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        dstBmp.compress(Bitmap.CompressFormat.JPEG, 10, stream);

                        byte[] array = stream.toByteArray();
                        base64Photo = Base64.encodeToString(array, 0);


                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    } catch (NullPointerException e) {
                        Toast.makeText(getApplicationContext(), "Foto är obligatoriskt", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Item newBook = new Book(null,
                            title.getText().toString(),
                            edition.getText().toString(),
                            author.getText().toString(),
                            Long.parseLong(isbn.getText().toString()),
                            description.getText().toString(),
                            base64Photo,
                            Integer.parseInt(price.getText().toString()),
                            null,
                            (String) condition.getSelectedItem(),
                            dateString);

                    Database.insertNewListing(newBook);
                    Toast.makeText(getApplicationContext(), "Annons uppladdad", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
    }

    private boolean conditions(EditText title, EditText author, EditText edition, EditText isbn, EditText price) {
        String sTitle = title.getText().toString();
        String sAuthor = author.getText().toString();
        String sEdition = edition.getText().toString();
        String sIsbn = isbn.getText().toString();
        String sPrice = price.getText().toString();

        if (sTitle.isEmpty()) {
            title.setError("Titel är obligatorisk");
            return false;
        }
        if (sAuthor.isEmpty()) {
            author.setError("Författare är obligatorisk");
            return false;
        }
        if (!sAuthor.matches(("^[a-zA-Z\\s]*$"))) {
            author.setError("Författare får endast innehålla bokstäver");
            return false;
        }
        if (sEdition.isEmpty()) {
            edition.setError("Upplaga är obligatorisk");
            return false;
        }
        if (sIsbn.isEmpty()) {
            isbn.setError("ISBN är obligatoriskt");
            return false;
        }
        if (sIsbn.length() != 13 && sIsbn.length() != 10) {
            isbn.setError("ISBN ej korrekt");
            return false;
        }
        if (sPrice.isEmpty()) {
            price.setError("Pris är obligatoriskt");
            return false;
        }

        return true;
    }

}


