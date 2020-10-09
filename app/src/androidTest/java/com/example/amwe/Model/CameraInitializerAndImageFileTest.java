package com.example.amwe.Model;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.amwe.BuildConfig;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class  CameraInitializerAndImageFileTest {
    Context context =InstrumentationRegistry.getInstrumentation().getContext();
    CameraInitializer initializer = new CameraInitializer(context,context.getExternalFilesDir(MediaStore.Images.ImageColumns.RELATIVE_PATH));
    @Test
    public void createNewFileCreatesAFile() throws IOException {
        File temp =initializer.createPhotoFile();
        assertEquals(true,temp.exists());
    }
  /*  @Test
    public void checkFileName() throws IOException {
        File temp =initializer.createPhotoFile();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        assertEquals("JPEG_" + timeStamp + "_"+".jpg",temp.getName());
    }*/
 /* @Test
    public void checkURI() throws IOException {
      File tmp = initializer.createPhotoFile();
      assertEquals(FileProvider.getUriForFile(context,
              BuildConfig.APPLICATION_ID + ".provider",
              tmp),initializer.getURI());
  }*/
    }

