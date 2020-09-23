package com.example.amwe.view;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.amwe.BuildConfig;
import com.example.amwe.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class AddListing extends AppCompatActivity {
    ImageButton cameraClick;
    final int CAMERA_PIC_REQUEST = 2;
    String photoPath;
    File photoFile;
    Uri photoURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
        cameraClick = findViewById(R.id.uppload_image);
        cameraClick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //Uri outputFileUri = Uri.fromFile(new File(getExternalCacheDir().getPath(), "pickImageResult.jpeg"));
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                try {
                  photoFile=createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile!=null){
                    photoURI = FileProvider.getUriForFile(getApplicationContext(),
                            BuildConfig.APPLICATION_ID + ".provider",
                            photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
                }
            }
        });
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
            cameraClick.setImageBitmap(thumbnail);}

        }
    }

    //SimpleDateFormat requires a newer Api than we are developing for, probably easy to fix but will do later.
    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(MediaStore.Images.ImageColumns.RELATIVE_PATH);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = image.getAbsolutePath();
        return image;


    }
}
