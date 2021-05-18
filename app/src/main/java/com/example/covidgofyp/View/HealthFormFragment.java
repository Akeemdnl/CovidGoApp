package com.example.covidgofyp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.covidgofyp.Model.User;
import com.example.covidgofyp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HealthFormFragment extends Fragment {

    private TextInputEditText healthFullname, healthPhoneNum, healthNRIC, healthAddress, healthDescription;
    private Button btnsubmitHealth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseDatabase rootNode;
    private String userId;
    private ConstraintLayout layout;

    public HealthFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_form, container, false);
        healthFullname = view.findViewById(R.id.healthFullname);
        healthPhoneNum = view.findViewById(R.id.healthPhoneNum);
        healthNRIC = view.findViewById(R.id.healthNRIC);
        healthAddress = view.findViewById(R.id.healthAddress);
        healthDescription = view.findViewById(R.id.healthDescription);
        btnsubmitHealth = view.findViewById(R.id.btnSubmitHealth);
        layout = view.findViewById(R.id.healthFormLayout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String fullname = userProfile.fullname;
                    healthFullname.setText(fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showSnackbar("Database Error retrieving information");
            }
        });

        btnsubmitHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    private void submitForm() {

    }

    private void showSnackbar(String message) {
        Snackbar.make(layout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("DISMISS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //close snackbar
                    }
                }).show();
    }
}