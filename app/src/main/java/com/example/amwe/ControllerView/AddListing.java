package com.example.amwe.ControllerView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;


import com.example.amwe.R;
import com.example.amwe.model.Book;
import com.example.amwe.model.Camera;
import com.example.amwe.model.Database;
import com.example.amwe.model.Item;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Date;

import java.io.File;
import java.io.IOException;

public class AddListing extends AppCompatActivity {
    private ImageButton cameraClick;
    private final int CAMERA_PIC_REQUEST = 2;
    private String photoPath;
    private File photoFile;
    private Uri photoURI;


    @RequiresApi(api = Build.VERSION_CODES.N)
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
        EditText condition = findViewById(R.id.input_condition);

        //TODO: Some blanks to fill in: image, price, condition

        mSubmit.setOnClickListener(submit(title, author, edition, isbn, description, price, condition));

        cameraClick.setOnClickListener(camera());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            if(photoURI!=null){
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
                Camera camera =new Camera(getApplicationContext(),getExternalFilesDir(MediaStore.Images.ImageColumns.RELATIVE_PATH));
                try {
                    photoFile = camera.createPhotoFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    photoURI = camera.getURI();
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                }
            }
        };
    }


    private View.OnClickListener submit(final EditText title,
                                        final EditText author,
                                        final EditText edition,
                                        final EditText isbn,
                                        final EditText description,
                                        final EditText price,
                                        final EditText condition) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat dateFormat = DateFormat.getDateInstance();
                String dateString = dateFormat.format(new Date());

                String base64Photo;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                            getApplicationContext().getContentResolver(),
                            photoURI);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);

                    byte[] array = stream.toByteArray();
                    base64Photo = Base64.encodeToString(array, 0);


                } catch (IOException e) {
                    e.printStackTrace();
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
                        condition.getText().toString(),
                        dateString);

                Database.insertNewListing(newBook);
                finish();
            }
        };
    }
}


