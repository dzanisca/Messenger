package com.dzanisca.dzaniscamess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private Button nextButton, verificationButton, registerButton;
    private EditText phoneInput, verificationCodeInput;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nextButton = (Button) findViewById(R.id.login_next_button);
        registerButton = (Button) findViewById(R.id.register_next_button);
        verificationButton = (Button) findViewById(R.id.login_verification_button);
        verificationCodeInput = (EditText) findViewById(R.id.login_verification_input);
        phoneInput = (EditText) findViewById(R.id.login_phone_input);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        nextButton.setOnClickListener(v -> {

            String phoneNumber = phoneInput.getText().toString();
            if(TextUtils.isEmpty(phoneNumber)){
                Toast.makeText(LoginActivity.this, "?????????????????? ??????????", Toast.LENGTH_SHORT).show();
            }
            else {
                loadingBar.setTitle("???????????????? ????????????");
                loadingBar.setMessage("????????????????????, ??????????????????");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(phoneNumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(LoginActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        verificationButton.setOnClickListener(v -> {
            String verificationCode = verificationCodeInput.getText().toString();
            if (TextUtils.isEmpty(verificationCode))
            {
                Toast.makeText(LoginActivity.this, "?????????????? ??????", Toast.LENGTH_SHORT).show();
            }
            else {
                loadingBar.setTitle("???????????????? ????????");
                loadingBar.setMessage("????????????????????, ??????????????????");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                signInWithPhoneAuthCredential(credential);
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                loadingBar.dismiss();

                Toast.makeText(LoginActivity.this, "???????????? ????????????", Toast.LENGTH_SHORT).show();
                nextButton.setVisibility(View.VISIBLE);
                verificationButton.setVisibility(View.INVISIBLE);
                verificationCodeInput.setVisibility(View.INVISIBLE);
                phoneInput.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;

                loadingBar.dismiss();

                Toast.makeText(LoginActivity.this, "?????? ??????????????????", Toast.LENGTH_SHORT).show();
                nextButton.setVisibility(View.INVISIBLE);
                verificationButton.setVisibility(View.VISIBLE);
                verificationCodeInput.setVisibility(View.VISIBLE);
                phoneInput.setVisibility(View.INVISIBLE);

            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "???????????????? ???????????? ??????????????", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "???????????? ???????????????? ????????????" , Toast.LENGTH_SHORT).show();
                    }
                });
    }

}