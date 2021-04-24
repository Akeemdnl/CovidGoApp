package com.example.covidgofyp.View;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidgofyp.Model.CovidData;
import com.example.covidgofyp.Model.CovidGlobalData;
import com.example.covidgofyp.Model.TopCountriesData;
import com.example.covidgofyp.R;
import com.example.covidgofyp.ViewModel.MainViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private Chip chipStats, chipNews;
    private String totalCases, activeCases, totalRecovered, totalDeaths, newCases, newRecovered, newDeaths, critical;
    private String globalTotal, globalActive, globalRecovered, globalDeaths;
    private CombinedChart chart;
   // private Fragment newsFragment = new NewsFragment();

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
        chipStats = view.findViewById(R.id.chipStats);
        chipNews = view.findViewById(R.id.chipNews);
        chipStats.setChecked(true);
        chipNews.setChecked(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MainViewModel mainViewModel;
        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MainViewModel.class);

        chipNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipStats.setChecked(false);
                chipNews.setChecked(true);
                Fragment newsFragment = new NewsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container, newsFragment,null )
                        .addToBackStack(null)
                        .commit();
            }
        });

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

        //Top ten countries graph
        mainViewModel.getChartData().observe(this, new Observer<List<TopCountriesData>>() {
            @Override
            public void onChanged(List<TopCountriesData> topCountriesDataList) {
                //Reminder: Try to put logic in ViewModel
                TopCountriesData topCountriesData;
                ArrayList<String> country = new ArrayList<String>();
                ArrayList<BarEntry> BarValueSet = new ArrayList<BarEntry>();
                ArrayList<Entry> LineValueSet = new ArrayList<>();
                CombinedData combinedData = new CombinedData();
                LineData lineData = new LineData();

                for(int i=0; i<10; i++){
                    topCountriesData = topCountriesDataList.get(i);
                    country.add(topCountriesData.getCountry());
                    BarEntry val = new BarEntry(i, Float.parseFloat(topCountriesData.getCases()));
                    BarValueSet.add(val);
                    LineValueSet.add(new Entry(i, Float.parseFloat(topCountriesData.getDeaths())));
                }

                BarDataSet barDataSet = new BarDataSet(BarValueSet, "Total Cases");
                barDataSet.setColor(Color.rgb(255,0,0));
                BarData barData = new BarData(barDataSet);
                LineDataSet lineDataSet = new LineDataSet(LineValueSet,"Total Deaths");
                lineDataSet.setColor(Color.rgb(0,0,0));
                lineDataSet.setCircleColor(Color.rgb(255,255,0));
                lineData.addDataSet(lineDataSet);

                Description description = new Description();
                description.setText(" ");
                combinedData.setData(barData);
                combinedData.setData(lineData);

                chart.setDescription(description);
                chart.setData(combinedData);

                XAxis xAxis = chart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(country));
                xAxis.setPosition(XAxis.XAxisPosition.TOP);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(country.size());
                xAxis.setLabelRotationAngle(270);
                chart.setExtraOffsets(5,20,5,0);
                chart.animateY(2000);
                chart.invalidate();

            }
        });
    }
}