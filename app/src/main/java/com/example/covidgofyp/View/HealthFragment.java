package com.example.covidgofyp.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.covidgofyp.R;

public class HealthFragment extends Fragment {

    private CardView cvApplyHealth, cvStatusHealth;
    private Fragment healthFormFragment = new HealthFormFragment();
    private Fragment healthStatusFragment = new HealthStatusFragment();
    public HealthFragment() {
        //Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page3, container, false);
        cvApplyHealth = view.findViewById(R.id.cvApplyHealth);
        cvStatusHealth = view.findViewById(R.id.cvStatusHealth);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvApplyHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container, healthFormFragment,null )
                        .addToBackStack(null)
                        .commit();
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
