package com.example.covidgofyp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.covidgofyp.Model.User;
import com.example.covidgofyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnCreateAcc;
    private TextInputEditText etFullname, etUsername, etEmail, etPassword, etConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        btnCreateAcc = findViewById(R.id.btnCreatAcc);
        etFullname = findViewById(R.id.etFullname);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createAccount();
            }
        });
    }

    private void createAccount() {
        String fullName = etFullname.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        Boolean validation;

        validation = checkValidation(fullName, username, email, password, confirmPassword);

        if(!validation){
            return;
        }else {
            //Firebase create new user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(fullName, username, email);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CreateAccountActivity.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                            //redirect to login, send email verification
                                            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(CreateAccountActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(CreateAccountActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }

    private Boolean checkValidation(String fullName, String username, String email, String password, String confirmPassword) {
        if(fullName.isEmpty()){
            etFullname.setError("Full Name is Required");
            etFullname.requestFocus();
            return false;
        }

        if(username.isEmpty()){
            etUsername.setError("Username is required");
            etUsername.requestFocus();
            return false;
        }

        if(email.isEmpty()){
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please provide valid email");
            etEmail.requestFocus();
            return false;
        }

        if(password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }

        if(password.length() < 6){
            etPassword.setError("Minimum password length is 6 characters");
            etPassword.requestFocus();
            return false;
        }

        if(confirmPassword.isEmpty()){
            etConfirmPassword.setError("Confirm Password is required");
            etConfirmPassword.requestFocus();
            return false;
        }

        if(!confirmPassword.equals(password)){
            etConfirmPassword.setError("Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }


}