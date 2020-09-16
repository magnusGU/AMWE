package com.example.amwe.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amwe.R;
import com.google.firebase.auth.FirebaseAuth;

public class EmailLogin extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mConfirm;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        Button loginButton = findViewById(R.id.log_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailLogin.this, MainActivity.class));
            }
        });
        mConfirm = findViewById(R.id.button_register);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailLogin.this, Register.class));
            }
        });
    }
}