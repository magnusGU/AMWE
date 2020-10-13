package com.example.amwe.ControllerView.AccountPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
                } catch (Exception e){
                    e.printStackTrace();
                }

                name = findViewById(R.id.change_name);
                name.setText((CharSequence) snapshot.child("name").getValue());

                eMail = findViewById(R.id.change_email);
                eMail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                newPassword1 = findViewById(R.id.new_password1);
                newPassword2 = findViewById(R.id.new_password2);

                Button saveButton = findViewById(R.id.save_button);
                saveButton.setOnClickListener(save(snapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

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
            if (srcBmp.getWidth() >= srcBmp.getHeight()){

                dstBmp = Bitmap.createBitmap(
                        srcBmp,
                        srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                        0,
                        srcBmp.getHeight(),
                        srcBmp.getHeight()
                );

            }else{
                dstBmp = Bitmap.createBitmap(
                        srcBmp,
                        0,
                        srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                        srcBmp.getWidth(),
                        srcBmp.getWidth()
                );
            }
            mProfilePicture.setImageBitmap(srcBmp);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private View.OnClickListener save(final DataSnapshot snapshot) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String sName = name.getText().toString();
                String sEMail = eMail.getText().toString();
                String sPassword = newPassword1.getText().toString();
                String sPassword2 = newPassword2.getText().toString();

                user.updateEmail(eMail.getText().toString());

                Database.getCurrentUser().child("name").setValue(sName);
                final String base64Photo;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                            getApplicationContext().getContentResolver(),
                            imageUri);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);

                    byte[] array = stream.toByteArray();
                    base64Photo = Base64.encodeToString(array, 0);


                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }catch (NullPointerException e){
                    e.printStackTrace();
                    return;
                }

                Database.getCurrentUser().child("userImage").setValue(base64Photo);

                updatePassword(sEMail, sPassword, sPassword2, user);

            }
        };
    }

    /**
     * Checks if the conditions to create an account are fulfilled.
     *
     * @param email     The email address that the user wants to register.
     * @param password1 The password that the user wants to have.
     * @param password2 The confirmation of the password that the user wants to have.
     */
    private void updatePassword(String email, String password1, String password2, FirebaseUser user) {
        if (TextUtils.isEmpty(email)) {
            eMail.setError("E-post är obligatoriskt");
            return;
        }

        if (TextUtils.isEmpty(password1)) {
            newPassword1.setError("Lösenord är obligatoriskt");
            return;
        }

        if (password1.length() <= 6) {
            newPassword1.setError("Lösenord måste vara mer än 6 tecken");
        }

        if (TextUtils.isEmpty(password2)) {
            newPassword2.setError("Lösenord är obligatoriskt");
            return;
        }

        if (!password1.equals(password2)) {
            newPassword2.setError("Lösenord ej samma");
            return;
        }

        user.updatePassword(password1);
        Toast.makeText(Settings.this, "Ändringar har sparats", Toast.LENGTH_SHORT).show();

        newPassword1.setText(null);
        newPassword2.setText(null);
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