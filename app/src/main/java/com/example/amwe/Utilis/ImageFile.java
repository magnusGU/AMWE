package com.example.amwe.Utilis;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that creates a file and stores it in the storageDir.
 */
public class ImageFile {
    private final File storageDir;

    /**
     * Constructor for ImageFile
     *
     * @param storageDir, the place where the file should be stored.
     */
    public ImageFile(File storageDir) {
        this.storageDir = storageDir;

    }


    /**
     * @return File. A new file with a unique name that is stored on the internal hard drive.
     * @throws IOException because of accessing the hard drive of the device.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";


        // Save a file: path for use with ACTION_VIEW intents

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


    }
}
