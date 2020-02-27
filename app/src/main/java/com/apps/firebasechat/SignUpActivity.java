package com.apps.firebasechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUp(View view) {
        EditText etName  = findViewById(R.id.full_name);
        EditText etEmail  = findViewById(R.id.email);
        EditText etPasswd = findViewById(R.id.password);

        String name     = etName.getText().toString();
        String email    = etEmail.getText().toString();
        String password = etPasswd.getText().toString();

        if(TextUtils.isEmpty(name)) {
            etName.setError("Name cannot be empty");
            etName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)) {
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password) || password.length() < 6) {
            etPasswd.setError("Password should be more than 6 characters");
            etPasswd.requestFocus();
            return;
        }

        createUser(name, email, password);
    }

    private void createUser(final String name, final String email, String password) {

        LoadingFragment.show(this);

        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveUser(name, email, task.getResult().getUser().getUid());
                        } else {
                            LoadingFragment.hide();
                            showToast(task.getException());
                        }
                    }
                });
    }

    private void saveUser(String name, String email, String uid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference usersRef = reference.push();

        User user = new User(name, email, uid, usersRef.getKey());
        usersRef.setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        LoadingFragment.hide();
                        if (task.isSuccessful()) {
                            launchSignInActivity();
                        } else {
                            showToast(task.getException());
                        }
                    }
                });
    }

    private String errorMessage(Exception e) {
        if (e == null) {
            return "Sign up failed";
        } else {
            return "Sign up failed " + e.getMessage();
        }
    }

    private void showToast(Exception e) {
        Toast.makeText(this, errorMessage(e), Toast.LENGTH_SHORT).show();
    }

    private void launchSignInActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
