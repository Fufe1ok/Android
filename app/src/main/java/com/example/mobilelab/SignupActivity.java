package com.example.mobilelab;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    public static final String regName = "^[A-Za-z]+$";
    private TextInputLayout emailField;
    private TextInputLayout nameField;
    private TextInputLayout phoneField;
    private TextInputLayout passField;
    static final String emailHint = "Email";
    static final String nameHint = "Name";
    static final String phoneHint = "Phone";
    static final String passHint = "Password";
    private boolean exit = false;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        auth = FirebaseAuth.getInstance();

        initFields();
        setHints();

        findViewById(R.id.btn_signup).setOnClickListener(v -> {
            final String email = Objects.requireNonNull(emailField.getEditText()).getText().toString();
            final String name = Objects.requireNonNull(nameField.getEditText()).getText().toString();
            final String phone = Objects.requireNonNull(phoneField.getEditText()).getText().toString();
            final String pass = Objects.requireNonNull(passField.getEditText()).getText().toString();
            signUp(email, name, phone, pass);
        });

        findViewById(R.id.link_login).setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    private void signUp(final String email, final String name, final String phone, final String pass) {
        if (!validate(email, name, phone, pass))
            return;

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        onCreateSuccess(name);
                    } else {
                        onCreateError();
                    }
                });
    }

    private void onCreateSuccess(final String name) {
        FirebaseUser user = auth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        if (user != null) {
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(this, DataListFragment.class));
                        }
                    });
        }
    }

    private void onCreateError() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Sign up failed");
        alertDialog.setMessage("This email or phone are already registered, please try another");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


    private void initFields() {
        emailField = findViewById(R.id.email_wrapper);
        nameField = findViewById(R.id.name_wrapper);
        phoneField = findViewById(R.id.phone_wrapper);
        passField = findViewById(R.id.pass_wrapper);
    }


    private void setHints() {
        emailField.setHint(emailHint);
        nameField.setHint(nameHint);
        phoneField.setHint(phoneHint);
        passField.setHint(passHint);
    }

    public boolean validate(final String email, final String name, final String phone, final String password) {
        boolean valid = true;
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError("Enter a valid email");
            valid = false;
        } else {
            emailField.setError(null);
        }

        if (phone.isEmpty() || !android.util.Patterns.PHONE.matcher(phone).matches()) {
            phoneField.setError("Enter a valid phone");
            valid = false;
        } else {
            phoneField.setError(null);
        }

        if (!name.matches(regName)) {
            nameField.setError("Enter a real name, please");
            valid = false;
        } else {
            nameField.setError(null);
        }

        if (password.isEmpty() || password.length() < LoginActivity.NECESSARY_COUNT) {
            passField.setError("At least 8 characters");
            valid = false;
        } else {
            passField.setError(null);
        }

        return valid;
    }

    public void onBackPressed() {
        if (exit) {
            finish();
            moveTaskToBack(true);
            new Handler().postDelayed(() -> exit = false, 3 * 1000);
        }
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) getApplication());
    }

}
