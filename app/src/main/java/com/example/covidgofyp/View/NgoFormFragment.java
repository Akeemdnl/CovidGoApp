package com.example.covidgofyp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.covidgofyp.Model.NgoForm;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NgoFormFragment extends Fragment {

    private TextInputEditText ngoFullName, ngoPhoneNum, ngoNRIC, ngoAddress, ngoAidDescription, ngoOccupation, ngoEmail;
    private AutoCompleteTextView ngoMarital, ngoSalary;
    private Button btnSubmitNgo;
    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseDatabase rootNode;
    private String userId;
    private String username;
    private String userEmail;
    private ConstraintLayout layout;

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
        ngoMarital = view.findViewById(R.id.ngoMarital);
        ngoSalary = view.findViewById(R.id.ngoSalary);
        ngoOccupation = view.findViewById(R.id.ngoOccupation);
        ngoEmail = view.findViewById(R.id.ngoEmail);
        btnSubmitNgo = view.findViewById(R.id.btnSubmitNgo);
        layout = view.findViewById(R.id.ngoFormLayout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        userEmail = user.getEmail();

        ArrayList<String> maritalList = getMarital();
        ArrayList<String> salaryRange = getSalary();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_marital, maritalList);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.dropdown_marital, salaryRange);
        ngoMarital.setAdapter(adapter);
        ngoSalary.setAdapter(adapter1);


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String fullname = userProfile.fullname;
                    String phone = userProfile.phone;
                    username = userProfile.username;
                    ngoFullName.setText(fullname);
                    ngoPhoneNum.setText(phone);
                    ngoEmail.setText(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
                showSnackbar("Database Error");
            }
        });

        btnSubmitNgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm(username);
            }
        });
    }

    private ArrayList<String> getSalary() {
        ArrayList<String> salary = new ArrayList<>();
        salary.add("< RM1000");
        salary.add("> RM1000 and < RM3000");
        salary.add("> RM3000");
        return salary;
    }

    private ArrayList<String> getMarital() {
        ArrayList<String> marital = new ArrayList<>();
        marital.add("Married");
        marital.add("Single");
        return marital;
    }

    private void submitForm(String username) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sumbangan");

        String fullname = ngoFullName.getText().toString();
        String phoneNum = ngoPhoneNum.getText().toString();
        String nric = ngoNRIC.getText().toString();
        String address = ngoAddress.getText().toString();
        String aidDescription = ngoAidDescription.getText().toString();
        String maritalStatus = ngoMarital.getText().toString();
        String occupation = ngoOccupation.getText().toString();
        String salary = ngoSalary.getText().toString();
        String email = ngoEmail.getText().toString();

        Toast.makeText(getContext(), maritalStatus, Toast.LENGTH_SHORT).show();
        String status = "Processing";
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String currentDate = formatter.format(date);

        Boolean validation = checkValidation(fullname,phoneNum,nric,address,aidDescription, maritalStatus, occupation, salary, email);
        if (salary.equals("< 1000")){
            salary = "1";
        }else if (salary.equals("> 1000 and < 3000")){
            salary = "2";
        }else if (salary.equals("> 3000")){
            salary = "3";
        }

        if(validation){
            // reference.push().setValue(ngoForm);
            //Improve Key Retreival!!
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Sumbangan").child(userId);
            String key = ref.push().getKey();
            NgoForm ngoForm = new NgoForm(fullname, phoneNum, nric, address, aidDescription, userId, status, currentDate, username, key, maritalStatus, occupation, salary, email);
            ref.child(key).setValue(ngoForm);

            //Toast.makeText(getContext(), "Your form has been sent for verification", Toast.LENGTH_SHORT).show();
            showSnackbar("Your form has been sent for verification");
            Fragment secondFragment = new NgoFragment();
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

    private Boolean checkValidation(String fullname, String phoneNum, String nric, String address, String aidDescription, String maritalStatus, String occupation, String salary, String email) {
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
        if (maritalStatus.isEmpty()){
            ngoMarital.setError("Marital status is required");
            ngoMarital.requestFocus();
            return false;
        }
        if (occupation.isEmpty()){
            ngoOccupation.setError("Marital status is required");
            ngoOccupation.requestFocus();
            return false;
        }
        if (salary.isEmpty()){
            ngoSalary.setError("Marital status is required");
            ngoSalary.requestFocus();
            return false;
        }
        if (email.isEmpty()){
            ngoEmail.setError("Marital status is required");
            ngoEmail.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ngoEmail.setError("Please provide valid email");
            ngoEmail.requestFocus();
            return false;
        }
        return true;
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