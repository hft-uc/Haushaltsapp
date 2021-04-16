package com.example.haushaltsapp.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.UserSummary;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

    private List<UserSummary> items = Collections.emptyList();

    public void updateItems(List<UserSummary> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Nonnull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.contentview.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView contentview;
        public UserSummary item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            contentview = view.findViewById(R.id.user_list_item_name);
        }

        @Nonnull
        @Override
        public String toString() {
            return super.toString() + " '" + contentview.getText() + "'";
        }
    }
}