package com.example.covidgofyp.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidgofyp.Model.CovidData;
import com.example.covidgofyp.R;
import com.example.covidgofyp.ViewModel.MainViewModel;

import java.util.List;

public class SecondFragment extends Fragment {

    CardView cvApplyNgo, cvStatusNgo;
    Fragment ngoFormFragment = new NgoFormFragment();
    Fragment ngoStatusFragment = new NgoStatusFragment();

    //Required empty constructor
    public SecondFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page2, container, false);
        cvApplyNgo = view.findViewById(R.id.cvApplyNgo);
        cvStatusNgo = view.findViewById(R.id.cvStatusNgo);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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


