package com.example.haushaltsapp.shopping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.ShoppingListEntry;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import javax.annotation.Nonnull;

public class ShoppingDetailEntriesRecyclerViewAdapter
    extends FirestoreRecyclerAdapter<ShoppingListEntry, ShoppingDetailEntriesRecyclerViewAdapter.ViewHolder> {

    private final EntryUnDoneListener listener;

    public ShoppingDetailEntriesRecyclerViewAdapter(
        @NonNull FirestoreRecyclerOptions<ShoppingListEntry> options,
        EntryUnDoneListener listener
    ) {
        super(options);
        this.listener = listener;
    }

    @Nonnull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_shopping_detail_entries_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ShoppingListEntry model) {
        holder.item = model;
        holder.name.setText(model.getName());
        holder.done.setVisibility(model.isDone() ? View.VISIBLE : View.INVISIBLE);
    }

    public interface EntryUnDoneListener {
        void onClick(ShoppingListEntry item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView name;
        public final ImageView done;
        public ShoppingListEntry item;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            name = view.findViewById(R.id.shopping_entry_name);
            done = view.findViewById(R.id.shopping_entry_done);
            view.setOnClickListener(v -> listener.onClick(item));
        }

        @Nonnull
        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}