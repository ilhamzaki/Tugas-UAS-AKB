package com.ilhamzaki.note;

// 10119006 - Ilham zaki - IF1

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActiviy extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activiy);

        ActionBar ab = getSupportActionBar();
        if (ab != null)  ab.setTitle("Sign Up");

        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.sign_up_email);
        inputPassword = (EditText) findViewById(R.id.sign_up_password);
        btnSignIn = (Button) findViewById(R.id.btn_goto_sign_in);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if (email.isEmpty()) inputEmail.setError("email harus diisi!");
                else if (password.isEmpty()) inputPassword.setError("password harus diisi!");
                else if (password.length() < 6) inputPassword.setError("password harus > 6");
                else {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActiviy.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActiviy.this, "User dengan Email & Password berhasil dibuat", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActiviy.this, SignInActivity.class));
                                    } else {
                                        Toast.makeText(SignUpActiviy.this, "Otentikasi Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActiviy.this, SignInActivity.class));
            }
        });

    }
}