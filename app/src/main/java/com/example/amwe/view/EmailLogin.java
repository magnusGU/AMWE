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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailLogin extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        fAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);

        Button mConfirm = findViewById(R.id.login_button);
        mConfirm.setOnClickListener(login());

        Button mRegister = findViewById(R.id.button_register);
        mRegister.setOnClickListener(register());
    }

    private View.OnClickListener register() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailLogin.this, Register.class));
            }
        };
    }


    private View.OnClickListener login() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("E-post är obligatoriskt");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Lösenord är obligatoriskt");
                    return;
                }


                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            System.out.println("Signed in");
                            startActivity(new Intent(EmailLogin.this, MainActivity.class));
                        }
                        else {
                            Toast.makeText(EmailLogin.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //startActivity(new Intent(EmailLogin.this, Register.class));
            }
        };
    }
}