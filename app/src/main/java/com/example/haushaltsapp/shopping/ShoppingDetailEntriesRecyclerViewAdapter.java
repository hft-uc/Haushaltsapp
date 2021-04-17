package com.example.haushaltsapp.shopping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.ShoppingListEntry;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

public class ShoppingDetailEntriesRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingDetailEntriesRecyclerViewAdapter.ViewHolder> {

    private List<ShoppingListEntry> items = Collections.emptyList();

    public void updateItems(List<ShoppingListEntry> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Nonnull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_shopping_detail_entries_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.name.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView name;
        public ShoppingListEntry item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            name = view.findViewById(R.id.content);
        }

        @Nonnull
        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}