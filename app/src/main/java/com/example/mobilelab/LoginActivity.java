package com.example.mobilelab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    public static final int necessaryCount=8;
    private TextInputLayout emailField;
    private TextInputLayout passField;

    private FirebaseAuth auth;

    public boolean validate(final String email, final String password) {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError("Enter a valid email");
            valid = false;
        } else {
            emailField.setError(null);
        }

        if (password.isEmpty() || password.length() < necessaryCount) {
            passField.setError("At least 8 characters");
            valid = false;
        } else {
            passField.setError(null);
        }

        return valid;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        auth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.email_wrapper);
        emailField.setHint("Email");
        passField = findViewById(R.id.pass_wrapper);
        passField.setHint("Password");


        findViewById(R.id.link_signup).setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            final String email = Objects.requireNonNull(emailField.getEditText()).getText().toString();
            final String pass = Objects.requireNonNull(passField.getEditText()).getText().toString();
            signIn(email, pass);
        });


    }

    private void signIn(final String email, final String pass) {
        if (!validate(email, pass))
            return;

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful())
                        onSignInSuccess();
                    else
                        onSignInError();
                });
    }



    private void onSignInError() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Authentication failed");
        alertDialog.setMessage("Please check your email and password");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }



    private void onSignInSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }
}

