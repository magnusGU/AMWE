package com.example.amwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amwe.ControllerView.Login;
import com.example.amwe.Model.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logOutButton = findViewById(R.id.sign_out);
        logOutButton.setOnClickListener(logOut());
        Database.getCurrentUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final EditText name = findViewById(R.id.change_name);
                name.setText((CharSequence) snapshot.child("name").getValue());

                EditText eMail = findViewById(R.id.change_email);
                eMail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                Button saveButton = findViewById(R.id.save_button);
                saveButton.setOnClickListener(save(snapshot, name, eMail));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private View.OnClickListener save(final DataSnapshot snapshot, final EditText name, final EditText eMail) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().getCurrentUser().updateEmail(eMail.getText().toString());

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/name/", name.getText().toString());
                Database.getDatabase().getReference().updateChildren(childUpdates);
            }
        };
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