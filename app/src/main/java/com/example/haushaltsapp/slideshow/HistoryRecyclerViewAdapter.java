package com.example.haushaltsapp.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.Expenditure;

import java.util.List;

public class HistoryRecyclerViewAdapter  extends RecyclerView.Adapter<ExpenditureHolder>{

    private final String uid;
    private final FragmentActivity fragmentActivity;
    private List<Expenditure> Expenditure;

    //List<com.example.haushaltsapp.types.Expenditure> expenditure
    public HistoryRecyclerViewAdapter(String uid, FragmentActivity fragmentActivity) {
        this.uid = uid;
        this.fragmentActivity = fragmentActivity;
       // Expenditure = expenditure;
    }


    @NonNull
    @Override
    public ExpenditureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(fragmentActivity);
        View view = inflater.inflate(R.layout.entry_list, parent, false);
        return new ExpenditureHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureHolder holder, int position) {
    holder.price.setText("432");
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
