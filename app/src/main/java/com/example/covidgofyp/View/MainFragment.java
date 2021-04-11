package com.example.covidgofyp.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.covidgofyp.Model.CovidData;
import com.example.covidgofyp.Model.CovidGlobalData;
import com.example.covidgofyp.R;
import com.example.covidgofyp.ViewModel.MainViewModel;
import com.github.mikephil.charting.charts.BarChart;

public class MainFragment extends Fragment  {
    private TextView tvTotalCases,
            tvActiveCases,
            tvTotalRecovered,
            tvTotalDeaths,
            tvNewCases,
            tvNewRecovered,
            tvNewDeaths,
            tvCritical,
            tvGlobalTotal,
            tvGlobalActive,
            tvGlobalRecovered,
            tvGlobalDeaths;
    private String totalCases, activeCases, totalRecovered, totalDeaths, newCases, newRecovered, newDeaths, critical;
    private String globalTotal, globalActive, globalRecovered, globalDeaths;
    private BarChart chart;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tvTotalCases = view.findViewById(R.id.tvTotalCases);
        tvActiveCases = view.findViewById(R.id.tvActiveCases);
        tvTotalRecovered = view.findViewById(R.id.tvTotalRecovered);
        tvTotalDeaths = view.findViewById(R.id.tvTotalDeaths);
        tvNewCases = view.findViewById(R.id.tvNewCases);
        tvNewRecovered = view.findViewById(R.id.tvNewRecovered);
        tvNewDeaths = view.findViewById(R.id.tvNewDeaths);
        tvCritical = view.findViewById(R.id.tvCritical);
        tvGlobalTotal = view.findViewById(R.id.tvGlobalTotal);
        tvGlobalActive = view.findViewById(R.id.tvGlobalActive);
        tvGlobalRecovered = view.findViewById(R.id.tvGlobalReovered);
        tvGlobalDeaths = view.findViewById(R.id.tvGlobalDeaths);
        chart = view.findViewById(R.id.chart);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewModel mainViewModel;
        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MainViewModel.class);

        //Observer to observe changes in ViewModel
        mainViewModel.getCovidData().observe(this, new Observer<CovidData>() {
            @Override
            public void onChanged(CovidData covidData) {
                totalCases = covidData.getTotalCases();
                activeCases = covidData.getActiveCases();
                totalRecovered = covidData.getTotalRecovered();
                totalDeaths = covidData.getTotalDeaths();
                newCases = covidData.getNewCases();
                newRecovered = covidData.getNewRecovered();
                newDeaths = covidData.getNewDeaths();
                critical = covidData.getCritical();

                tvTotalCases.setText(totalCases);
                tvActiveCases.setText(activeCases);
                tvTotalRecovered.setText(totalRecovered);
                tvTotalDeaths.setText(totalDeaths);

                if(!newCases.equals("0")){
                    tvNewCases.setText("+"+newCases);
                }else {
                    tvNewCases.setVisibility(View.GONE);
                }
                if(!newRecovered.equals("0")){
                    tvNewRecovered.setText("+"+newRecovered);
                }else {
                    tvNewRecovered.setVisibility(View.GONE);
                }
                if(!newDeaths.equals("0")){
                    tvNewDeaths.setText("+"+newDeaths);
                }else {
                    tvNewDeaths.setVisibility(View.GONE);
                }
                tvCritical.setText(critical);
            }
        });

        mainViewModel.getCovidGlobalData().observe(this, new Observer<CovidGlobalData>() {
            @Override
            public void onChanged(CovidGlobalData covidGlobalData) {
                globalTotal = covidGlobalData.getTotalCases();
                globalActive = covidGlobalData.getActiveCases();
                globalRecovered = covidGlobalData.getTotalRecovered();
                globalDeaths = covidGlobalData.getTotalDeaths();

                tvGlobalTotal.setText(globalTotal);
                tvGlobalActive.setText(globalActive);
                tvGlobalRecovered.setText(globalRecovered);
                tvGlobalDeaths.setText(globalDeaths);
            }
        });
    }
}