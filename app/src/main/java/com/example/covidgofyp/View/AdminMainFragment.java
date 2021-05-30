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
import android.widget.TextView;

import com.example.covidgofyp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdminMainFragment extends Fragment {

    DatabaseReference reference;
    CardView cvNgoApplication, cvHealthApplication, cvUsers;
    ImageButton btnSignOut;
    TextView counter;
    Fragment adminNgoApplication = new AdminNgoApplicationFragment();
    Fragment adminUsers = new AdminUserFragment();
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
        cvUsers = view.findViewById(R.id.cvUsers);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        counter = view.findViewById(R.id.counter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reference = FirebaseDatabase.getInstance().getReference("Sumbangan");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                        if (childSnapshot.exists()){
                            System.out.println(childSnapshot.toString());
                            sum++;
                            //sum = (int) childSnapshot.getChildrenCount();
                        }
                    }
                }
                counter.setText(Integer.toString(sum));
//                int sum = 0;
//                if (snapshot.exists()){
//                    sum = (int) snapshot.getChildrenCount();
//
//                    counter.setText(Integer.toString(sum));
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cvNgoApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(adminNgoApplication);
            }
        });

        cvUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(adminUsers);
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