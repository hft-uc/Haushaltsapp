package com.example.haushaltsapp.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.Transaction;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class HistoryRecyclerViewAdapter extends FirestoreRecyclerAdapter<Transaction, HistoryRecyclerViewAdapter.ViewHolder> {


    //List<com.example.haushaltsapp.types.Expenditure> expenditure
    public HistoryRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<Transaction> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull HistoryRecyclerViewAdapter.ViewHolder holder, int position, @NonNull @NotNull com.example.haushaltsapp.types.Transaction model) {
        holder.item = model;
        holder.entry.setText(model.getName());
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_list, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView date;
        final TextView price;
        final ImageView iconImageView;
        final TextView entry;
        final TextView category;
        final TextView user;
        public View view;
        public Transaction item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            price = itemView.findViewById(R.id.price_tw);
            entry = itemView.findViewById(R.id.entry_tw);
            date = itemView.findViewById(R.id.date_tw);
            iconImageView = itemView.findViewById(R.id.categoryImage_tw);
            category = itemView.findViewById(R.id.category_tw);
            user = itemView.findViewById(R.id.user_tw);
        }


    }
}
