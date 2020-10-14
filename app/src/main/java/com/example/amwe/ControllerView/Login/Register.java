package com.example.amwe.ControllerView.Login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

import com.example.amwe.Model.Database.Database;
import com.example.amwe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Register extends AppCompatActivity {

    private ImageView mProfilePicture;
    private EditText mName, mEmail, mPassword1, mPassword2;
    private FirebaseAuth fAuth;

    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mProfilePicture = findViewById(R.id.profile_picture);
        mProfilePicture.setOnClickListener(uploadImage());

        mName = findViewById(R.id.change_name);
        mEmail = findViewById(R.id.change_email);
        mPassword1 = findViewById(R.id.old_password);
        mPassword2 = findViewById(R.id.new_password1);
        Button mConfirm = findViewById(R.id.register_button);

        fAuth = FirebaseAuth.getInstance();

        mConfirm.setOnClickListener(register());
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
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * Registers the user's account.
     *
     * @return A View.OnClickListener that should be applied to the register-button.
     */
    private View.OnClickListener register() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password1 = mPassword1.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();

                //conditions(email, password1, password2);

                if (conditions(name, email, password1, password2)) {
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
                    } catch (NullPointerException e){
                        e.printStackTrace();
                        return;
                    }


                    fAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                UserProfileChangeRequest.Builder updateinfo = new UserProfileChangeRequest
                                        .Builder().setDisplayName(name);
                                task.getResult().getUser().updateProfile(updateinfo.build());
                                Database.addUser(fAuth.getCurrentUser().getUid(), name, base64Photo);
                                startActivity(new Intent(Register.this, EmailLogin.class));
                                fAuth.signOut();
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Registrering misslyckades", Toast.LENGTH_SHORT);
                                View view = toast.getView();
                                view.getBackground().setColorFilter(Color.rgb(244, 67, 54), PorterDuff.Mode.SRC_IN);
                                toast.show();
                            }
                        }
                    });
                }

            }
        };
    }

    /**
     * Checks if the conditions to create an account are fulfilled.
     * @param name The complete name of the user.
     * @param email The email address that the user wants to register.
     * @param password1 The password that the user wants to have.
     * @param password2 The confirmation of the password that the user wants to have.
     * @return True if conditions are fulfilled, else false.
     */
    private boolean conditions(String name, String email, String password1, String password2) {
        if (name.isEmpty() || !name.contains(" ")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Fullständigt namn är obligatoriskt", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(244, 67, 54), PorterDuff.Mode.SRC_IN);
            toast.show();
            return false;
        }
        if (!name.matches(("^[a-zA-Z\\s]*$"))) {
            Toast toast = Toast.makeText(getApplicationContext(), "Namn får endast innehålla bokstäver", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(244, 67, 54), PorterDuff.Mode.SRC_IN);
            toast.show();
            return false;
        }
        if (email.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "E-post är obligatorisk", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(244, 67, 54), PorterDuff.Mode.SRC_IN);
            toast.show();
            return false;
        }
        if (!email.contains("@") || !email.contains(".")) {
            Toast toast = Toast.makeText(getApplicationContext(), "E-post ej korrekt", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(244, 67, 54), PorterDuff.Mode.SRC_IN);
            toast.show();
            return false;
        }
        if (password1.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Lösenord är obligatoriskt", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(244, 67, 54), PorterDuff.Mode.SRC_IN);
            toast.show();
            return false;
        }
        if (password2.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Bekräfta lösenord", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(244, 67, 54), PorterDuff.Mode.SRC_IN);
            toast.show();
            return false;
        }
        if (!password1.equals(password2)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Lösenord ej samma", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(244, 67, 54), PorterDuff.Mode.SRC_IN);
            toast.show();
            return false;
        }

        Toast toast = Toast.makeText(getApplicationContext(), "Konto registrerat", Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.getBackground().setColorFilter(Color.rgb(139, 195, 74), PorterDuff.Mode.SRC_IN);
        toast.show();
        return true;
    }

    /*
    private void conditions(String email, String password1, String password2) {
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("E-post är obligatoriskt");
            return;
        }

        if (TextUtils.isEmpty(password1)) {
            mPassword1.setError("Lösenord är obligatoriskt");
            return;
        }

        if (TextUtils.isEmpty(password2)) {
            mPassword2.setError("Lösenord är obligatoriskt");
            return;
        }

        if (!password1.equals(password2)) {
            mPassword2.setError("Lösenord ej samma");
            return;
        }

        if (password1.length() <= 6) {
            mPassword1.setError("Lösenord måste vara mer än 6 tecken");
        }
    }
     */

}