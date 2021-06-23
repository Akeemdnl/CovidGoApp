package com.example.covidgofyp.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.covidgofyp.Model.CovidData;
import com.example.covidgofyp.Model.CovidGlobalData;
import com.example.covidgofyp.Model.TimelineData;
import com.example.covidgofyp.Model.TopCountriesData;
import com.example.covidgofyp.R;
import com.example.covidgofyp.ViewModel.MainViewModel;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Time;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CovidFragment extends Fragment  {
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
    private LineChart lineChart;
    private ImageButton btnSignOut;
    private double dTotalCases, dActiveCases, dTotalRecovered, dTotalDeaths, dNewCases, dNewRecovered, dNewDeaths, dCritical;
    private double dGlobalTotal, dGlobalActive, dGlobalRecovered, dGlobalDeaths;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    private ScrollView scrollView;
    private CardView cvTopBar;

    public CovidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_covid, container, false);
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
        lineChart = view.findViewById(R.id.chartTimeline);
        chipStats = view.findViewById(R.id.chipStats);
        chipNews = view.findViewById(R.id.chipNews);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        scrollView = view.findViewById(R.id.scrollView);
        cvTopBar = view.findViewById(R.id.cvTopBar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewModel mainViewModel;
        mainViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MainViewModel.class);
        chipStats.setChecked(true);
        chipNews.setChecked(false);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                float y = 0;
                if (scrollView != null){
                    if(scrollView.getScrollY() > y){
                        cvTopBar.setVisibility(View.GONE);
                    }else {
                        cvTopBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

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

        chipStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipStats.setChecked(true);
                chipNews.setChecked(false);
            }
        });

        //Observer to observe changes in ViewModel
        mainViewModel.getCovidData().observe(this, new Observer<CovidData>() {
            @Override
            public void onChanged(CovidData covidData) {
                numberFormat.setGroupingUsed(true);

                totalCases = covidData.getTotalCases();
                dTotalCases = Double.parseDouble(totalCases);

                activeCases = covidData.getActiveCases();
                dActiveCases = Double.parseDouble(activeCases);

                totalRecovered = covidData.getTotalRecovered();
                dTotalRecovered = Double.parseDouble(totalRecovered);

                totalDeaths = covidData.getTotalDeaths();
                dTotalDeaths = Double.parseDouble(totalDeaths);

                newCases = covidData.getNewCases();
                dNewCases = Double.parseDouble(newCases);

                newRecovered = covidData.getNewRecovered();
                dNewRecovered = Double.parseDouble(newRecovered);

                newDeaths = covidData.getNewDeaths();
                dNewDeaths = Double.parseDouble(newDeaths);

                critical = covidData.getCritical();
                dCritical = Double.parseDouble(critical);

                tvTotalCases.setText(numberFormat.format(dTotalCases));
                tvActiveCases.setText(numberFormat.format(dActiveCases));
                tvTotalRecovered.setText(numberFormat.format(dTotalRecovered));
                tvTotalDeaths.setText(numberFormat.format(dTotalDeaths));

                if(!newCases.equals("0")){
                    tvNewCases.setText("+"+numberFormat.format(dNewCases));
                }else {
                    tvNewCases.setVisibility(View.GONE);
                }
                if(!newRecovered.equals("0")){
                    tvNewRecovered.setText("+"+numberFormat.format(dNewRecovered));
                }else {
                    tvNewRecovered.setVisibility(View.GONE);
                }
                if(!newDeaths.equals("0")){
                    tvNewDeaths.setText("+"+numberFormat.format(dNewDeaths));
                }else {
                    tvNewDeaths.setVisibility(View.GONE);
                }

                tvCritical.setText(numberFormat.format(dCritical));
            }
        });

        mainViewModel.getCovidGlobalData().observe(this, new Observer<CovidGlobalData>() {
            @Override
            public void onChanged(CovidGlobalData covidGlobalData) {
                numberFormat.setGroupingUsed(true);

                globalTotal = covidGlobalData.getTotalCases();
                dGlobalTotal = Double.parseDouble(globalTotal);

                globalActive = covidGlobalData.getActiveCases();
                dGlobalActive = Double.parseDouble(globalActive);

                globalRecovered = covidGlobalData.getTotalRecovered();
                dGlobalRecovered = Double.parseDouble(globalRecovered);

                globalDeaths = covidGlobalData.getTotalDeaths();
                dGlobalDeaths = Double.parseDouble(globalDeaths);

                tvGlobalTotal.setText(numberFormat.format(dGlobalTotal));
                tvGlobalActive.setText(numberFormat.format(dGlobalActive));
                tvGlobalRecovered.setText(numberFormat.format(dGlobalRecovered));
                tvGlobalDeaths.setText(numberFormat.format(dGlobalDeaths));
            }
        });

        mainViewModel.getLineChartData().observe(this, new Observer<List<TimelineData>>() {
            @Override
            public void onChanged(List<TimelineData> timelineData) {
                List<Entry> entries = new ArrayList<>();
                TimelineData data;
                ArrayList<String> date = new ArrayList<>();
                ArrayList<Integer> test = new ArrayList<Integer>();
                LineData lineData = new LineData();
                for (int i = 0; i < timelineData.size(); i++){
                    data = timelineData.get(i);
                    date.add(data.getDate());
                    int cases = Integer.parseInt(data.getCases());
                    test.add(cases);
                    entries.add(new Entry(i, cases));
                }

                LineDataSet lineDataSet = new LineDataSet(entries, "Cases");
                lineDataSet.setColor(Color.parseColor("#7d7d7d"));
                lineDataSet.setCircleColor(Color.RED);//Dot color
                lineDataSet.setLineWidth(1f);//Line width
                lineDataSet.setValueTextSize(11f);

                YAxis rightAxis = lineChart.getAxisRight();

                //Set the y-axis on the right of the chart to be disabled
                rightAxis.setEnabled(false);
                YAxis leftAxis = lineChart.getAxisLeft();
                //Set the y-axis on the left of the chart to be disabled
                leftAxis.setEnabled(false);
                //Set the x axis
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setTextColor(Color.parseColor("#333333"));
                xAxis.setTextSize(5f);
                xAxis.setAxisMinimum(0f);
                xAxis.setDrawAxisLine(true);//Whether to draw the axis
                xAxis.setDrawGridLines(false);//Set the line corresponding to each point on the x-axis
                xAxis.setDrawLabels(true);//Draw label refers to the corresponding value on the x axis
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//Set the display position of the x axis
                xAxis.setGranularity(1f);//X-axis label redrawing is prohibited after zooming in
                xAxis.setValueFormatter(new IndexAxisValueFormatter(date));
                xAxis.setLabelRotationAngle(270);
                xAxis.setLabelCount(date.size());

                Description description = new Description();
                description.setText("Last 15 days");
                description.setTextSize(15f);
                description.setEnabled(true);
                lineChart.setDescription(description);

                for (int i = 0; i < test.size(); i++){
                    System.out.println(i+": "+test.get(i));
                }

                lineData.addDataSet(lineDataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

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

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog();
            }
        });
    }

    private void displayDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("Sign Out")
                .setBackground(getResources().getDrawable(R.drawable.dialog_background))
                .setMessage("Are you sure?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }).show();
    }

}