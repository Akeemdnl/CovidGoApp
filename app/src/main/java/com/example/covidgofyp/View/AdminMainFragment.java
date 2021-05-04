package com.example.covidgofyp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidgofyp.R;


public class AdminMainFragment extends Fragment {
    CardView cvNgoApplication, cvHealthApplication;
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