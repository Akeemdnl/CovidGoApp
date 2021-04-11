package com.example.covidgofyp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.covidgofyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView btmNav;
    FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btmNav = findViewById(R.id.bottom_nav);
        container = findViewById(R.id.container);

        final Fragment mainFragment = new MainFragment();
        final Fragment secondFragment = new SecondFragment();
        final Fragment thirdFragment = new ThirdFragment();

        if(savedInstanceState == null) {
            FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
            fragmentManager
                    .setReorderingAllowed(true)
                    .replace(R.id.container, mainFragment, null)
                    .addToBackStack("optional")
                    .commit();
        }

        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

              FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
              switch (item.getItemId()){
                  case R.id.page_1 :
                      fragmentManager
                              .setReorderingAllowed(true)
                              .replace(R.id.container, mainFragment, null)
                              .addToBackStack(null)
                              .commit();
                      return true;

                  case R.id.page_2 :
                     /* TextView view = findViewById(R.id.page_1);
                      view.setTextColor(Color.GREEN);*/
                      fragmentManager
                              .setReorderingAllowed(true)
                              .replace(R.id.container, secondFragment, null)
                              .addToBackStack(null)
                              .commit();
                      return true;

                  case R.id.page_3 :
                      fragmentManager
                              .setReorderingAllowed(true)
                              .replace(R.id.container,  thirdFragment, null )
                              .addToBackStack(null)
                              .commit();
                      return true;
              }
              return false;

            }
        });


    }
}