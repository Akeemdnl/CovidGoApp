package com.example.covidgofyp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.covidgofyp.Model.User;
import com.example.covidgofyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView btmNav;
    private FrameLayout container;
    private FirebaseUser user;
    private DatabaseReference dbReference;
    private String userID;
    //Test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btmNav = findViewById(R.id.bottom_nav);
        container = findViewById(R.id.container);

        Fragment mainFragment = new MainFragment();
        final Fragment secondFragment = new SecondFragment();
        final Fragment thirdFragment = new ThirdFragment();

        if(savedInstanceState == null) {
            FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
            fragmentManager
                    .setReorderingAllowed(true)
                    .replace(R.id.container, mainFragment, null)
                    .addToBackStack(null)
                    .commit();
        }

        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              switch (item.getItemId()){
                  case R.id.page_1 :
                        getFragment(mainFragment);
                      return true;

                  case R.id.page_2 :
                      getFragment(secondFragment);
                      return true;

                  case R.id.page_3 :
                      getFragment(thirdFragment);
                      return true;
              }
              return false;

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.userAction :
                user = FirebaseAuth.getInstance().getCurrentUser();
                dbReference = FirebaseDatabase.getInstance().getReference("Users");
                userID = user.getUid();

                dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile = snapshot.getValue(User.class);

                        if(userProfile != null){
                            String fullname = userProfile.fullname;
                            String username = userProfile.username;
                            String email = userProfile.email;

                            fullname = "Fullname: "+fullname;
                            username = "Username: "+username;
                            email = "Email: "+ email;
                            displayDialog(fullname, username, email);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                });

        }
        return super.onOptionsItemSelected(item);
    }

    private void displayDialog(String fullname, String username, String email) {


        String[] userInfo = {fullname, username, email};

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
        builder.setTitle("User Information")
                .setIcon(R.drawable.user_icon)
                .setBackground(getResources().getDrawable(R.drawable.dialog_background))
                .setItems(userInfo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayDialog(fullname, username, email);
                    }
                })
                .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Dismiss dialog box
                    }
                }).show();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void getFragment(Fragment fragment){
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager
                .setReorderingAllowed(true)
                .replace(R.id.container, fragment, null)
                .addToBackStack(null)
                .commit();
    }
}