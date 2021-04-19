package com.example.covidgofyp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidgofyp.R;
import com.google.android.material.chip.Chip;

public class NewsFragment extends Fragment {

    private Fragment mainFragment = new MainFragment();
    private Chip chipStats, chipNews;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        chipNews = view.findViewById(R.id.chipNews);
        chipStats = view.findViewById(R.id.chipStats);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chipStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipNews.setChecked(false);
                chipStats.setChecked(true);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container, mainFragment,null )
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}