package com.example.covidgofyp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.covidgofyp.Model.User;
import com.example.covidgofyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private DatabaseReference dbReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                    dbReference = FirebaseDatabase.getInstance().getReference("Users");
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    dbReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userProfile = snapshot.getValue(User.class);
                            String type = userProfile.type;
                            String status = userProfile.status;

                            if (type.equals("admin")){
                                startActivity(new Intent(SplashScreen.this, AdminActivity.class));
                            }else if (status.equals("Disabled")){
                                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                            }else {
                                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(SplashScreen.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                }
            }
        }, SPLASH_TIME_OUT);
    }
}