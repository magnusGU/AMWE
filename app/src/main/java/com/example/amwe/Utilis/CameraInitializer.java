package com.example.amwe.Utilis;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.example.amwe.BuildConfig;

import java.io.File;
import java.io.IOException;

/**
 * This class handles the initialization of a file that will contain the image taken by the camera and
 * also return the file and its location.
 */

public class CameraInitializer {
    private final Context context;
    private final File storageDir;
    private Uri photoURI;
    private File photoFile;
    public CameraInitializer(Context context, File storagedir){
        this.context=context;
        this.storageDir=storagedir;
    }

    /**
     *
     * @return a .jpg File that is unique and used to store an image
     * @throws IOException Exception that is needed to access hard drive of device, because of createImageFile().
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public File createPhotoFile() throws IOException {
        ImageFile file = new ImageFile(storageDir);
        this.photoFile = file.createImageFile();
        return photoFile;

    }

    /**
     *
     * @return a file path to the stored file.
     */
    public Uri getURI() {
        Uri photoURI;
        return photoURI = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                photoFile);

    }


}
