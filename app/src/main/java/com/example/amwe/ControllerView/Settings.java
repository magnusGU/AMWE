package com.example.amwe.ControllerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amwe.Model.Database;
import com.example.amwe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity {

    EditText name;
    EditText eMail;
    EditText newPassword1;
    EditText newPassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logOutButton = findViewById(R.id.sign_out);
        logOutButton.setOnClickListener(logOut());
        Database.getCurrentUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

                UserProfileChangeRequest.Builder updateinfo = new UserProfileChangeRequest.Builder().setDisplayName(sName);
                user.updateProfile(updateinfo.build());

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/users/" + user.getUid() + "/name/", sName);
                Database.getDatabase().getReference().updateChildren(childUpdates);

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