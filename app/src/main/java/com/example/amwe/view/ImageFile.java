package com.example.amwe.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that creates a file and stores it in the storageDir.
 */
public class ImageFile {
    private File storageDir;

    /**
     *
     * @param storageDir, the place where the file should be stored.
     */
    public ImageFile(File storageDir){
        this.storageDir=storageDir;

    }




    /**
     *
     * @return File. A new file with a unique name that is stored on the internal harddrive.
     * @throws IOException
     * SimpleDateFormat requires a newer Api than we are developing for, probably easy to fix but will do later.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";


        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents

        return image;


    }
}
