package com.example.covidgofyp.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.covidgofyp.Model.NgoForm;
import com.example.covidgofyp.Model.User;
import com.example.covidgofyp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NgoFormFragment extends Fragment {

    private TextInputEditText ngoFullName, ngoPhoneNum, ngoNRIC, ngoAddress, ngoAidDescription;
    private Button btnSubmitNgo;
    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseDatabase rootNode;
    private String userId;
    private String username;

    public NgoFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ngo_form, container, false);
        ngoFullName = view.findViewById(R.id.ngoFullname);
        ngoPhoneNum = view.findViewById(R.id.ngoPhoneNum);
        ngoNRIC = view.findViewById(R.id.ngoNRIC);
        ngoAddress = view.findViewById(R.id.ngoAddress);
        ngoAidDescription = view.findViewById(R.id.ngoAidDescription);
        btnSubmitNgo = view.findViewById(R.id.btnSubmitNgo);
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
                if(userProfile != null){
                    String fullname = userProfile.fullname;
                    username = userProfile.username;
                    ngoFullName.setText(fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

        btnSubmitNgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm(username);
            }
        });
    }

    private void submitForm(String username) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sumbangan");

        String fullname = ngoFullName.getText().toString();
        String phoneNum = ngoPhoneNum.getText().toString();
        String nric = ngoNRIC.getText().toString();
        String address = ngoAddress.getText().toString();
        String aidDescription = ngoAidDescription.getText().toString();
        String status = "Processing";
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String currentDate = formatter.format(date);

        Boolean validation = checkValidation(fullname,phoneNum,nric,address,aidDescription);

        if(validation){
            NgoForm ngoForm = new NgoForm(fullname, phoneNum, nric, address, aidDescription, userId, status, currentDate, username);
            reference.push().setValue(ngoForm);
            FirebaseDatabase.getInstance().getReference("Sumbangan").child(userId).push().setValue(ngoForm);

            Toast.makeText(getContext(), "Your form has been sent for verification", Toast.LENGTH_SHORT).show();
            Fragment secondFragment = new SecondFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.container, secondFragment,null )
                    .addToBackStack(null)
                    .commit();
        }else {
            return;
        }
    }

    private Boolean checkValidation(String fullname, String phoneNum, String nric, String address, String aidDescription) {
        if (fullname.isEmpty()){
            ngoFullName.setError("Full name is required");
            ngoFullName.requestFocus();
            return false;
        }
        if (phoneNum.isEmpty()){
            ngoPhoneNum.setError("Phone Number is required");
            ngoPhoneNum.requestFocus();
            return false;
        }
        if (nric.isEmpty()){
            ngoNRIC.setError("IC number is required");
            ngoNRIC.requestFocus();
            return false;
        }
        if (address.isEmpty()){
            ngoAddress.setError("Address is required");
            ngoAddress.requestFocus();
            return false;
        }
        if (aidDescription.isEmpty()){
            ngoAidDescription.setError("Description is required");
            ngoAidDescription.requestFocus();
            return false;
        }
        return true;
    }
}