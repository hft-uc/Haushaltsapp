package com.example.haushaltsapp.shopping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.ShoppingListSummary;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

public class ShoppingListRecyclerViewAdapter
    extends FirestorePagingAdapter<ShoppingListSummary, ShoppingListRecyclerViewAdapter.ViewHolder> {

    public ShoppingListRecyclerViewAdapter(@NonNull FirestorePagingOptions<ShoppingListSummary> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ShoppingListSummary model) {
        holder.setItem(model);
        holder.contentView.setText(model.getName());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_shopping, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView contentView;
        private ShoppingListSummary item;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            contentView = view.findViewById(R.id.shopping_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }

        public void setItem(ShoppingListSummary item) {
            this.item = item;
        }
    }
}
