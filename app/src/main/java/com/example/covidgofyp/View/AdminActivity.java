package com.example.covidgofyp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.covidgofyp.R;


public class AdminActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Fragment adminMainFragment = new AdminMainFragment();

        if(savedInstanceState == null) {
            getFragment(adminMainFragment);
        }
    }

    private void getFragment(Fragment fragment){
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager
                .setReorderingAllowed(true)
                .replace(R.id.containerAdmin, fragment, null)
                .addToBackStack(null)
                .commit();
    }
}