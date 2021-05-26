package com.example.haushaltsapp.slideshow;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.haushaltsapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class GraphicFragment extends Fragment {


    private PieChart chart;
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
        financeViewModel = new ViewModelProvider(requireParentFragment()).get(FinanceViewModel.class);
        String id = financeViewModel.getid();
        financeViewModel.loadBudget(id);
        incTV = view.findViewById(R.id.textView_income);
        expTV = view.findViewById(R.id.textView_expense);
        ProgressBar = view.findViewById(R.id.progress_bar);
        float expensesSum = 0;
        float incomesSum = 0;
        Map<String, Double> categoryModels = financeViewModel.getTransactionsMap();
        Set<String> categoryModelsSet = categoryModels.keySet();
        Object[] tempObjectArray = categoryModelsSet.toArray();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < categoryModelsSet.size(); i++) {
            float temp = categoryModels.get(tempObjectArray[i]).floatValue();
            if (temp < 0) {
                expensesSum = expensesSum + temp;
                temp = temp * -1;
            } else {
                incomesSum = incomesSum + temp;
            }
            pieEntries.add(new PieEntry(temp, tempObjectArray[i].toString())
            );
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
        dataSet.setDrawValues(true);
        dataSet.setSliceSpace(2f);
        dataSet.setColors(colors);
        chart = view.findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
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
        chart.setTransparentCircleRadius(80f);
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();


        incTV.setText(String.valueOf(incomesSum));
        expTV.setText(String.valueOf(expensesSum));
        float progress = 100 * incomesSum / (float) (incomesSum - expensesSum);
        ProgressBar.setProgress((int) progress);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}