package com.example.covidgofyp.View;

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

public class NgoFragment extends Fragment {

    private CardView cvApplyNgo, cvStatusNgo;
    private Fragment ngoFormFragment = new NgoFormFragment();
    private Fragment ngoStatusFragment = new NgoStatusFragment();
    private TextView title;
    private FirebaseAuth mAuth;
    private DatabaseReference dbReference;
    private FirebaseUser user;
    private String userID;

    //Required empty constructor
    public NgoFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ngo, container, false);
        cvApplyNgo = view.findViewById(R.id.cvApplyNgo);
        cvStatusNgo = view.findViewById(R.id.cvStatusNgo);
        title = view.findViewById(R.id.tvNgoTitle);
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
                    title.setText("Hi " + username + ", here you can apply for help from non profit organizations");
                    title.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database error (NgoFragment)");
            }
        });


       cvApplyNgo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentManager fragmentManager = getFragmentManager();
               fragmentManager.beginTransaction()
                      .setReorderingAllowed(true)
                      .replace(R.id.container, ngoFormFragment,null )
                      .addToBackStack(null)
                      .commit();
           }
       });

       cvStatusNgo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentManager fragmentManager = getFragmentManager();
               fragmentManager.beginTransaction()
                       .setReorderingAllowed(true)
                       .replace(R.id.container, ngoStatusFragment,null )
                       .addToBackStack(null)
                       .commit();
           }
       });

    }
}


