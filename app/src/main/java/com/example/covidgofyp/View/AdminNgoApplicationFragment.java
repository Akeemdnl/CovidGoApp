package com.example.covidgofyp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.covidgofyp.Model.NgoForm;
import com.example.covidgofyp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminNgoApplicationFragment extends Fragment {

    FirebaseUser user;
    DatabaseReference reference;
    String userId;
    RecyclerView recyclerView;

    public AdminNgoApplicationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_ngo_application, container, false);
        recyclerView = view.findViewById(R.id.rvNgoApplication);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Sumbangan");
        userId = user.getUid();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<NgoForm> ngoFormList = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for (DataSnapshot childSnapshot :  dataSnapshot.getChildren()){
                        NgoForm ngoForm = childSnapshot.getValue(NgoForm.class);
                        if (ngoForm != null){
                            ngoFormList.add(ngoForm);
                        }
                    }
                }
                putDataInRecyclerView(ngoFormList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong: "+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

        private void putDataInRecyclerView(List<NgoForm> ngoFormList) {
        AdminNgoStatusAdapter adapter = new AdminNgoStatusAdapter(getContext(),ngoFormList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemDecorator decorator = new ItemDecorator(30);
        recyclerView.addItemDecoration(decorator);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}