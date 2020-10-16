package com.example.amwe.ControllerView.AccountPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amwe.ControllerView.Login.Login;
import com.example.amwe.Model.Database.Database;
import com.example.amwe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Settings extends AppCompatActivity {

    private ImageView mProfilePicture;
    private EditText name;
    private EditText eMail;
    private EditText newPassword1;
    private EditText newPassword2;

    private static final int PICK_IMAGE = 1;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logOutButton = findViewById(R.id.sign_out);
        logOutButton.setOnClickListener(logOut());
        Database.getCurrentUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mProfilePicture = findViewById(R.id.edit_profile_picture);
                mProfilePicture.setOnClickListener(uploadImage());

                try {
                    byte[] decodedString = Base64.decode((String) snapshot.child("userImage").getValue(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    mProfilePicture.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                name = findViewById(R.id.change_name);
                name.setText((CharSequence) snapshot.child("name").getValue());

                eMail = findViewById(R.id.change_email);
                eMail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                newPassword1 = findViewById(R.id.new_password1);
                newPassword2 = findViewById(R.id.new_password2);

                Button saveButton = findViewById(R.id.register_button);
                saveButton.setOnClickListener(save());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /**
     * Opens the gallery on the phone from where user can choose an image.
     *
     * @return A View.OnClickListener that should be applied to the UploadImage-button.
     */
    private View.OnClickListener uploadImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Välj profilbild"), PICK_IMAGE);
            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUri = data.getData();
        try {
            Bitmap srcBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

            Bitmap dstBmp;
            if (srcBmp.getWidth() >= srcBmp.getHeight()) {

                dstBmp = Bitmap.createBitmap(
                        srcBmp,
                        srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                        0,
                        srcBmp.getHeight(),
                        srcBmp.getHeight()
                );

            } else {
                dstBmp = Bitmap.createBitmap(
                        srcBmp,
                        0,
                        srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                        srcBmp.getWidth(),
                        srcBmp.getWidth()
                );
            }
            mProfilePicture.setImageBitmap(dstBmp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    /**
     * Saves the changes made to the profile.
     *
     * @return A View.OnClickListner that should be applied to the save-button.
     */
    private View.OnClickListener save() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (conditions()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    String sName = name.getText().toString();
                    String sPassword1 = newPassword1.getText().toString();
                    String sPassword2 = newPassword2.getText().toString();

                    user.updateEmail(eMail.getText().toString());

                    if (!sPassword1.isEmpty() || !sPassword2.isEmpty()) {
                        updatePassword(sPassword1, sPassword2, user);
                    }

                    Database.getCurrentUser().child("name").setValue(sName);
                    final String base64Photo;
                    try {
                        Bitmap srcBmp = MediaStore.Images.Media.getBitmap(
                                getApplicationContext().getContentResolver(),
                                imageUri);

                        Bitmap dstBmp;
                        if (srcBmp.getWidth() >= srcBmp.getHeight()) {

                            dstBmp = Bitmap.createBitmap(
                                    srcBmp,
                                    srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                                    0,
                                    srcBmp.getHeight(),
                                    srcBmp.getHeight()
                            );

                        } else {
                            dstBmp = Bitmap.createBitmap(
                                    srcBmp,
                                    0,
                                    srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                                    srcBmp.getWidth(),
                                    srcBmp.getWidth()
                            );
                        }

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        dstBmp.compress(Bitmap.CompressFormat.JPEG, 10, stream);

                        byte[] array = stream.toByteArray();
                        base64Photo = Base64.encodeToString(array, 0);


                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        return;
                    }

                    Database.getCurrentUser().child("userImage").setValue(base64Photo);
                }
            }
        };
    }


    /**
     * Checks if the conditions to create an account are fulfilled.
     *
     * @param sPassword1 The password that the user wants to have.
     * @param sPassword2 The confirmation of the password that the user wants to have.
     */
    private void updatePassword(String sPassword1, String sPassword2, FirebaseUser user) {

        if (sPassword1.length() < 6) {
            newPassword1.setError("Lösenord måste vara minst 6 tecken långt");
            return;
        }

        if (!sPassword1.equals(sPassword2)) {
            newPassword2.setError("Lösenord ej samma");
            return;
        }

        user.updatePassword(sPassword1);
        newPassword1.setText(null);
        newPassword2.setText(null);
        Toast.makeText(getApplicationContext(), "Lösenord sparat", Toast.LENGTH_SHORT).show();
    }


    /**
     * Checks if the conditions to update an account are fulfilled.
     *
     * @return True if conditions are fulfilled, else false.
     */
    private boolean conditions() {
        String sName = name.getText().toString();
        String sEmail = eMail.getText().toString();

        if (sName.isEmpty() || !sName.contains(" ")) {
            name.setError("Fullständigt namn är obligatoriskt");
            return false;
        }
        if (!sName.matches(("^[a-zA-ZåäöÅÄÖ\\s]*$"))) {
            name.setError("Namn får endast innehålla bokstäver");
            return false;
        }
        if (sEmail.isEmpty()) {
            eMail.setError("E-post är obligatorisk");
            return false;
        }
        if (!sEmail.contains("@") || !sEmail.contains(".")) {
            eMail.setError("E-post ej korrekt");
            return false;
        }

        Toast.makeText(getApplicationContext(), "Ändringar sparade", Toast.LENGTH_SHORT).show();

        return true;
    }


    /**
     * Signs out the current user from the application.
     */
    private View.OnClickListener logOut() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Settings.this, Login.class));
            }
        };
    }
}