package com.example.covidgofyp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.covidgofyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button btnReset;
    private TextInputEditText etEmail;
    private ProgressBar progressBar;
    private ConstraintLayout layout;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnReset = findViewById(R.id.btnReset);
        etEmail = findViewById(R.id.etEmail);
        progressBar = findViewById(R.id.progressBar);
        layout = findViewById(R.id.resetPassLayout);
        mAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email = etEmail.getText().toString().trim();

        if(email.isEmpty()){
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //try to improve reset password function
                if(task.isSuccessful()){
                    //Toast.makeText(ResetPasswordActivity.this, "Please check your email to reset your password", Toast.LENGTH_LONG).show();
                    Snackbar.make(layout, "Please check your email to reset your password", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));

                }else {
                    //Toast.makeText(ResetPasswordActivity.this, "Try again. Something went wrong.", Toast.LENGTH_LONG).show();
                    Snackbar.make(layout, "Try again. Something went wrong.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }
            }
        });

    }
}