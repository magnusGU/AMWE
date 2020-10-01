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
 * A temprary class to move code out of addListing.
 */
public class Camera {
    private Context context;
    private File storageDir;
    private Uri photoURI;
    private File photoFile;
    public Camera(Context context, File storagedir){
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
