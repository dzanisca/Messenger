package com.dzanisca.dzaniscamess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private Button loginBtn, registerBtn;
    private EditText emailET, passwordET;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginBtn = (Button) findViewById(R.id.login_button);
        registerBtn = (Button) findViewById(R.id.register_button);
        emailET = (EditText) findViewById(R.id.email_input);
        passwordET = (EditText) findViewById(R.id.password_input);

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAccount();
            }
        });
    }

    private void signInAccount() {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Заполните поле", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Заполните поле", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if(task.isSuccessful()){
                      Toast.makeText(RegisterActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                      startActivity(intent);
                      finish();
                  }
                  else {
                      String message = task.getException().toString();
                      Toast.makeText(RegisterActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                  }
                }
            });
        }
    }

    private void createAccount() {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Заполните поле", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Заполните поле", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Успешная регистрация", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        String message = task.getException().toString();
                        Toast.makeText(RegisterActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });


            }

        }
    }
