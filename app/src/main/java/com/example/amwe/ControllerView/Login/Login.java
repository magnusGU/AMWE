package com.example.amwe.ControllerView.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amwe.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button emailButton = findViewById(R.id.button_email);
        emailButton.setOnClickListener(openEmailLogin());
    }

    /**
     * Starts the EmailLogin activity.
     *
     * @return A View.OnClickListener that should be applied to the email-button.
     */
    private View.OnClickListener openEmailLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, EmailLogin.class));
            }
        };
    }
}