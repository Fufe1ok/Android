package com.example.mobilelab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;



public class LoginActivity extends AppCompatActivity {
    public static final int Necessary_count=8;
    private TextInputLayout emailField;
    private TextInputLayout passField;
    private Boolean exit = false;

    private FirebaseAuth auth;

    public boolean isValid(final String email, final String password) {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError(getText(R.string.emailError));
            valid = false;
        } else {
            emailField.setError(null);
        }

        if (password.isEmpty() || password.length() < Necessary_count) {
            passField.setError(getText(R.string.passError));
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
        emailField.setHint(getString(R.string.email));
        passField = findViewById(R.id.pass_wrapper);
        passField.setHint(getString(R.string.password));


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
        if (!isValid(email, pass))
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
        alertDialog.setTitle(getString(R.string.authError));
        alertDialog.setMessage(getString(R.string.checkError));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }



    private void onSignInSuccess() {
        startActivity(new Intent(this, MainActivity.class));

}

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            moveTaskToBack(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}

