package com.amwe.bokbytarapp.Utils;

import android.content.Context;
import android.net.Uri;

import androidx.core.content.FileProvider;

import com.amwe.bokbytarapp.BuildConfig;

import java.io.File;
import java.io.IOException;

/**
 * @author Elias Johansson
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
        return FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                photoFile);

    }


}
