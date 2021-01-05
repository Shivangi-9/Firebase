package com.example.shivangi.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity {

    EditText userEmail, userPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userEmail = findViewById(R.id.signUpPersonName);
        userPassword = findViewById(R.id.signUpPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickSignUp(View view){
        switch (view.getId()){
            case R.id.signUp:
                register();
                break;
            case R.id.suggestTxtView:
                startActivity(new Intent(this,MainActivity.class));
        }
    }

    private void register() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(SignUp.this,"Please enter an email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(SignUp.this,"Please enter a valid email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(SignUp.this,"Please enter a password!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(SignUp.this,"The Minimum length of a password is 6!",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this,"User registered successfully!",Toast.LENGTH_LONG).show();
                    userEmail.setText("");
                    userPassword.setText("");
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(SignUp.this,"You are already registered!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignUp.this,"Registration failed!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}