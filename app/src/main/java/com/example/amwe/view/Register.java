package com.example.amwe.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amwe.R;
import com.example.amwe.model.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText mName, mEmail, mPassword1, mPassword2;
    private Button mConfirm;
    private FirebaseAuth fAuth;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mName = findViewById(R.id.register_name);
        mEmail = findViewById(R.id.register_email);
        mPassword1 = findViewById(R.id.register_password_1);
        mPassword2 = findViewById(R.id.register_password_2);
        mConfirm = findViewById(R.id.register_confirm);

        fAuth = FirebaseAuth.getInstance();
        db = new Database();

        mConfirm.setOnClickListener(register());
    }

    private View.OnClickListener register() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password1 = mPassword1.getText().toString().trim();;
                String password2 = mPassword2.getText().toString().trim();;

                conditions(email, password1, password2);

                fAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println("Registered");
                        if (task.isSuccessful()){
                            db.addUser(fAuth.getCurrentUser().getUid(), name);
                            startActivity(new Intent(Register.this, EmailLogin.class));
                            fAuth.signOut();
                        }
                        else {
                            Toast.makeText(Register.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        };
    }

    private void conditions(String email, String password1, String password2 ) {
        if (TextUtils.isEmpty(email)){
            mEmail.setError("E-post är obligatoriskt");
            return;
        }

        if (TextUtils.isEmpty(password1)){
            mPassword1.setError("Lösenord är obligatoriskt");
            return;
        }

        if (TextUtils.isEmpty(password2)){
            mPassword2.setError("Lösenord är obligatoriskt");
            return;
        }

        if (!password1.equals(password2)){
            mPassword2.setError("Lösenord ej samma");
            return;
        }

        if (password1.length() <= 6){
            mPassword1.setError("Lösenord måste vara mer än 6 tecken");
            return;
        }
    }
}