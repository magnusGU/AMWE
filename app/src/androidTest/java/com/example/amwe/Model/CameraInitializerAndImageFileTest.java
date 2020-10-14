package com.example.amwe.Model;

import android.content.Context;
import android.provider.MediaStore;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.amwe.Utilis.CameraInitializer;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class  CameraInitializerAndImageFileTest {
    Context context =InstrumentationRegistry.getInstrumentation().getContext();
    CameraInitializer initializer = new CameraInitializer(context,context.getExternalFilesDir(MediaStore.Images.ImageColumns.RELATIVE_PATH));
    File temp;
    @Test
    public void createNewFileCreatesAFile() throws IOException {
        this.temp =initializer.createPhotoFile();
        assertEquals(true,temp.exists());
    }

    @Test
    public void isFileReadable() throws IOException {
        File temp =initializer.createPhotoFile();
        assertEquals(true,temp.canRead());
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

