package com.example.haushaltsapp.slideshow;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.haushaltsapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class GraphicFragment extends Fragment {

    public PieChart chart;
    public PieChart chartSpending;
    private FinanceViewModel financeViewModel;
    private TextView incTV;
    private TextView expTV;
    private ProgressBar ProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        financeViewModel = new ViewModelProvider(requireParentFragment()).get(FinanceViewModel.class);
        String id = financeViewModel.getid();
        financeViewModel.loadBudget(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_graphic, container, false);

        Switch simpleSwitch = view.findViewById(R.id.switch1);
        simpleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                Toast.makeText(GraphicFragment.this.getActivity(), "Ausgaben", Toast.LENGTH_SHORT).show();
                chart.setVisibility(View.INVISIBLE);
                chartSpending.setVisibility((View.VISIBLE));

            } else {
                Toast.makeText(GraphicFragment.this.getActivity(), "Einnahmen!", Toast.LENGTH_SHORT).show();
                chartSpending.setVisibility(View.INVISIBLE);
                chart.setVisibility((View.VISIBLE));
            }
        });
        financeViewModel = new ViewModelProvider(requireParentFragment()).get(FinanceViewModel.class);
        String id = financeViewModel.getid();
        financeViewModel.loadBudget(id);
        incTV = view.findViewById(R.id.textView_income);
        expTV = view.findViewById(R.id.textView_expense);
        ProgressBar = view.findViewById(R.id.progress_bar);
        float expensesSum = 0;
        float incomesSum = 0;
        float spending = 0;
        float earning = 0;
        Map<String, Double> categoryModels = financeViewModel.getTransactionsMap();
        Set<String> categoryModelsSet = categoryModels.keySet();
        Object[] tempObjectArray = categoryModelsSet.toArray();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntriesSpending = new ArrayList<>();
        for (int i = 0; i < categoryModelsSet.size(); i++) {
            float temp = categoryModels.get(tempObjectArray[i]).floatValue();
            if (temp < 0) {
                expensesSum = expensesSum + temp;
                spending = spending + temp * -1;
                pieEntriesSpending.add(new PieEntry(spending, tempObjectArray[i].toString()));
            } else {
                earning = earning + temp;
                incomesSum = incomesSum + temp;
                pieEntries.add(new PieEntry(earning, tempObjectArray[i].toString()));
            }

        }
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());

        PieDataSet dataSet = new PieDataSet(pieEntries, "Übersicht");
        PieDataSet dataSetSepnding = new PieDataSet(pieEntriesSpending, "Ausgaben");
        dataSetSepnding.setDrawValues(true);
        dataSetSepnding.setSliceSpace(2f);
        dataSetSepnding.setColors(colors);
        dataSet.setDrawValues(true);
        dataSet.setSliceSpace(2f);
        dataSet.setColors(colors);

        chart = view.findViewById(R.id.chart1);
        chartSpending = view.findViewById(R.id.chart2);
        chartSpending.setUsePercentValues(true);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        PieData data = new PieData(dataSet);
        PieData dataSpending = new PieData(dataSetSepnding);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        dataSpending.setValueFormatter(new PercentFormatter());
        dataSpending.setValueTextSize(11f);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setTouchEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setDrawEntryLabels(true);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setHoleRadius(50f);
        data.setValueTextColor(Color.BLACK);
        dataSpending.setValueTextColor(Color.BLACK);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setTransparentCircleRadius(80f);
        chartSpending.setExtraOffsets(5, 10, 5, 5);
        chartSpending.setHoleRadius(50f);
        chartSpending.setHoleColor(Color.WHITE);
        chartSpending.setEntryLabelColor(Color.BLACK);
        chartSpending.getDescription().setEnabled(false);
        chartSpending.setDragDecelerationFrictionCoef(0.95f);
        chartSpending.setTransparentCircleColor(Color.WHITE);
        chartSpending.setDrawHoleEnabled(true);
        chart.setData(data);
        chartSpending.setData(dataSpending);
        chartSpending.highlightValues(null);
        chartSpending.invalidate();
        chartSpending.getLegend().setEnabled(false);
        chartSpending.setUsePercentValues(true);
        chart.highlightValues(null);
        chart.invalidate();
        incTV.setText(String.valueOf(incomesSum));
        expTV.setText(String.valueOf(expensesSum));
        float progress = 100 * incomesSum / (incomesSum - expensesSum);
        ProgressBar.setProgress((int) progress);
        return view;
    }


}
