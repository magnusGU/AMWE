package com.example.amwe.ControllerView.Login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amwe.ControllerView.MainActivity;
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

    /**
     * Opens the Register activity if the button is pressed.
     *
     * @return A View.OnClickListener that should be applied to the register-button.
     */
    private View.OnClickListener register() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailLogin.this, Register.class));
            }
        };
    }


    /**
     * Signs in the user if the credentials are correct.
     *
     * @return A View.OnClickListener that should be applied to the login-button.
     */
    private View.OnClickListener login() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("E-post är obligatoriskt");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Lösenord är obligatoriskt");
                    return;
                }


                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Signed in");
                            startActivity(new Intent(EmailLogin.this, MainActivity.class));
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Inloggning misslyckades", Toast.LENGTH_SHORT);
                            View toastView = toast.getView();
                            toastView.getBackground().setColorFilter(Color.rgb(244, 67, 54), PorterDuff.Mode.SRC_IN);
                            toast.show();
                        }
                    }
                });
            }
        };
    }
}