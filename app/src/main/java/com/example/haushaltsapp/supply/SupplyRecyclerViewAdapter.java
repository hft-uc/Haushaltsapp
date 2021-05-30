package com.example.haushaltsapp.supply;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
//import com.example.haushaltsapp.supply.SupplyFragmentDirections;
import com.example.haushaltsapp.types.SupplySummary;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

public class SupplyRecyclerViewAdapter
        extends FirestorePagingAdapter<SupplySummary,
        com.example.haushaltsapp.supply.SupplyRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = com.example.haushaltsapp.supply.SupplyRecyclerViewAdapter
            .class.getCanonicalName();

    public SupplyRecyclerViewAdapter(@NonNull FirestorePagingOptions<SupplySummary> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull com.example.haushaltsapp.supply.SupplyRecyclerViewAdapter
            .ViewHolder holder, int position, @NonNull SupplySummary model) {
        holder.setItem(model);
        holder.contentView.setText(model.getName());
    }

    @NonNull
    @Override
    public com.example.haushaltsapp.supply.SupplyRecyclerViewAdapter
            .ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_supply, parent, false);

        return new com.example.haushaltsapp.supply.SupplyRecyclerViewAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView contentView;
        private SupplySummary item;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            contentView = view.findViewById(R.id.shopping_content);

            view.setOnClickListener(v -> {
                Log.i(TAG, "Navigating to supply with id " + item.getId());
                //Navigation.findNavController(v).navigate(SupplyFragmentDirections.actionNavShoppingToSupplyFragment(item.getId()));
            });
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }

        public void setItem(SupplySummary item) {
            this.item = item;
        }
    }
}



