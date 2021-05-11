package com.example.haushaltsapp.slideshow;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.Transaction;

public class ExpenditureHolder extends RecyclerView.ViewHolder {

    final TextView date;
    final TextView price;
    final ImageView iconImageView;
    final TextView entry;
    final TextView category;
    final TextView user;
    public View view;
    public Transaction item;

    public ExpenditureHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        price = itemView.findViewById(R.id.price_tw);
        entry = itemView.findViewById(R.id.entry_tw);
        date = itemView.findViewById(R.id.date_tw);
        iconImageView = itemView.findViewById(R.id.categoryImage_tw);
        category = itemView.findViewById(R.id.category_tw);
        user =itemView.findViewById(R.id.user_tw);
    }
}
