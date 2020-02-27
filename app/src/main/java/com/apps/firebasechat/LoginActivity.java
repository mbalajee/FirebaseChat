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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void signIn(View view) {

        EditText etUserName = findViewById(R.id.username);
        EditText etPassword = findViewById(R.id.password);

        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            etUserName.setError("Username cannot be empty");
            etUserName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return;
        }

        LoadingFragment.show(this);

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        LoadingFragment.hide();
                        if (task.isSuccessful()) {
                            launchHome();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed " + toastMessage(task.getException()), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void launchHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private String toastMessage(Exception ex) {
        if (ex == null) {
            return "Login failed";
        } else {
            return "Login failed " + ex.getLocalizedMessage();
        }
    }
}
