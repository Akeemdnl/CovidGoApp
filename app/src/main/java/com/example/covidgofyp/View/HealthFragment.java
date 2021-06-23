package com.example.covidgofyp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.covidgofyp.Model.User;
import com.example.covidgofyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HealthFragment extends Fragment {

    private CardView cvApplyHealth, cvStatusHealth;
    private Fragment healthFormFragment = new HealthFormFragment();
    private Fragment healthStatusFragment = new HealthStatusFragment();
    private Fragment healthFormFragmentNew = new HealthFormFragmentNew();
    private TextView title;
    private FirebaseAuth mAuth;
    private DatabaseReference dbReference;
    private FirebaseUser user;
    private String userID;

    public HealthFragment() {
        //Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_health, container, false);
        cvApplyHealth = view.findViewById(R.id.cvApplyHealth);
        cvStatusHealth = view.findViewById(R.id.cvStatusHealth);
        title = view.findViewById(R.id.tvHealthcareTitle);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        dbReference = FirebaseDatabase.getInstance().getReference("Users");

        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                String username = userProfile.username;
                if(username != null) {
                    title.setText("Hi " + username + ", Let's make an appointment with a doctor");
                    title.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database error (NgoFragment)");
            }
        });

        cvApplyHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .setReorderingAllowed(true)
//                        .replace(R.id.container, healthFormFragmentNew,null )
//                        .addToBackStack(null)
//                        .commit();

                getContext().startActivity(new Intent(getContext(), AppointmentMain.class));
            }
        });

        cvStatusHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container, healthStatusFragment,null )
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
