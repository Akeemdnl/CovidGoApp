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
import android.widget.ProgressBar;

import com.example.covidgofyp.Model.NgoForm;
import com.example.covidgofyp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class NgoStatusFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private RecyclerView recyclerView;
    private NgoStatusAdapter adapter;
    private ProgressBar progressBar;

    public NgoStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ngo_status, container, false);
        recyclerView = view.findViewById(R.id.rvNgoStatus);
        progressBar = view.findViewById(R.id.progressBarNgoStatus);
        progressBar.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference("Sumbangan").child(userId);
        FirebaseRecyclerOptions<NgoForm> options =
                new FirebaseRecyclerOptions.Builder<NgoForm>()
                .setQuery(reference, NgoForm.class)
                .build();

        putDataInRecyclerView(options);

    }

    private void putDataInRecyclerView(FirebaseRecyclerOptions<NgoForm> options) {
        adapter = new NgoStatusAdapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemDecorator decorator = new ItemDecorator(30);
        recyclerView.addItemDecoration(decorator);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}