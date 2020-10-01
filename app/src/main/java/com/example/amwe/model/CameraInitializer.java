package com.example.amwe.model;

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
    private Context context;
    private File storageDir;
    private Uri photoURI;
    private File photoFile;
    public CameraInitializer(Context context, File storagedir){
        this.context=context;
        this.storageDir=storagedir;

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public File createPhotoFile() throws IOException {
        ImageFile file = new ImageFile(storageDir);
        this.photoFile=file.createImageFile();
        return photoFile;

    }
    public Uri getURI(){
        return photoURI = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                photoFile);

    }


}
