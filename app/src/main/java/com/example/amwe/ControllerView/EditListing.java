package com.example.amwe.ControllerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amwe.Model.Book;
import com.example.amwe.Model.CameraInitializer;
import com.example.amwe.Model.Database;
import com.example.amwe.Model.Item;
import com.example.amwe.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class EditListing extends AppCompatActivity {

    private ImageButton cameraClick;
    private final int CAMERA_PIC_REQUEST = 2;
    private String photoPath;
    private File photoFile;
    private Uri photoURI;
    private String base64Photo;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listing);
        cameraClick = findViewById(R.id.edit_image);
        cameraClick.setOnClickListener(camera());

        initUI();
    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();

        String bookId = bundle.getString("bookId");
        String seller = bundle.getString("seller");

        Bitmap thumbnail = BitmapFactory.decodeByteArray(bundle.getByteArray("image"), 0, bundle.getByteArray("image").length);

        //Cast because of return type Object
        //  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        cameraClick.setImageBitmap(thumbnail);
        ViewGroup.LayoutParams params = cameraClick.getLayoutParams();
        params.height = 1500;
        cameraClick.setLayoutParams(params);

        base64Photo = Base64.encodeToString(bundle.getByteArray("image"), 0);


        EditText title = findViewById(R.id.edit_title);
        title.setText(bundle.getString("title"));

        EditText author = findViewById(R.id.edit_author);
        author.setText(bundle.getString("author"));

        EditText edition = findViewById(R.id.edit_edition);
        edition.setText(bundle.getString("edition"));

        EditText isbn = findViewById(R.id.edit_ISBN);
        isbn.setText(String.valueOf(bundle.getLong("isbn")));

        EditText condition = findViewById(R.id.edit_condition);
        condition.setText(bundle.getString("condition"));

        EditText price = findViewById(R.id.edit_price);
        price.setText(String.valueOf(bundle.getDouble("price")));

        EditText description = findViewById(R.id.edit_description);
        description.setText(bundle.getString("description"));

        Button save = findViewById(R.id.save_changes);
        save.setOnClickListener(saveChanges(title, author, edition, isbn, condition, price, description, bookId, seller));
    }


    /**
     * Saves the changes made when button is pressed.
     *
     * @param title       The title of the listing.
     * @param author      The author of the book.
     * @param edition     The edition of the book.
     * @param isbn        The ISBN of the book
     * @param condition   The condition of the book.
     * @param price       The price of the listing.
     * @param description The description for the listing.
     * @param bookId      The id of the listing.
     * @param seller      The seller of the listing.
     * @return A View.OnClickListener that should be applied to the save-button.
     */
    private View.OnClickListener saveChanges(final EditText title,
                                             final EditText author,
                                             final EditText edition,
                                             final EditText isbn,
                                             final EditText condition,
                                             final EditText price,
                                             final EditText description,
                                             final String bookId,
                                             final String seller) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat dateFormat = DateFormat.getDateInstance();
                String dateString = dateFormat.format(new Date());

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), photoURI);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);

                    byte[] array = stream.toByteArray();
                    base64Photo = Base64.encodeToString(array, 0);


                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (NullPointerException e){
                    System.out.println("Exception");
                }
                Item item = new Book(
                        bookId,
                        title.getText().toString(),
                        edition.getText().toString(),
                        author.getText().toString(),
                        Long.parseLong(isbn.getText().toString()),
                        description.getText().toString(),
                        base64Photo,
                        Double.parseDouble(price.getText().toString()),
                        seller,
                        condition.getText().toString(),
                        dateString
                );
                Database.updateListing(item);

                finish();
            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (photoURI != null) {
                File image = new File(photoFile.toURI());

                Bitmap thumbnail = BitmapFactory.decodeFile(image.getAbsolutePath());


                //Cast because of return type Object
                //  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                cameraClick.setImageBitmap(thumbnail);
                ViewGroup.LayoutParams params = cameraClick.getLayoutParams();
                params.height = 1500;
                cameraClick.setLayoutParams(params);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
}