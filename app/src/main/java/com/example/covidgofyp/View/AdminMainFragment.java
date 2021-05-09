package com.example.covidgofyp.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.covidgofyp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;


public class AdminMainFragment extends Fragment {
    CardView cvNgoApplication, cvHealthApplication;
    ImageButton btnSignOut;
    Fragment adminNgoApplication = new AdminNgoApplicationFragment();
    public AdminMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_main, container, false);
        cvNgoApplication = view.findViewById(R.id.cvNgoApplication);
        cvHealthApplication = view.findViewById(R.id.cvHealthApplication);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvNgoApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(adminNgoApplication);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog();
            }
        });
    }

    private void displayDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("Sign Out")
                .setBackground(getResources().getDrawable(R.drawable.dialog_background))
                .setMessage("Are you sure?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }).show();

    }

    private void getFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.containerAdmin, fragment,null )
                .addToBackStack(null)
                .commit();
    }
}