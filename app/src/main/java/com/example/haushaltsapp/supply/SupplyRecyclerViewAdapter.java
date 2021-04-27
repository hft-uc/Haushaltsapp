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
import com.example.haushaltsapp.shopping.ShoppingListFragmentDirections;
import com.example.haushaltsapp.shopping.ShoppingRecyclerViewAdapter;
import com.example.haushaltsapp.types.ShoppingListSummary;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

public class SupplyRecyclerViewAdapter
        extends FirestorePagingAdapter<ShoppingListSummary, SupplyRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = ShoppingRecyclerViewAdapter.class.getCanonicalName();

    public SupplyRecyclerViewAdapter(@NonNull FirestorePagingOptions<ShoppingListSummary> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull SupplyRecyclerViewAdapter.ViewHolder holder, int position, @NonNull ShoppingListSummary model) {
        holder.setItem(model);
        holder.contentView.setText(model.getName());
    }

    @NonNull
    @Override
    public SupplyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shopping_item, parent, false);

        return new SupplyRecyclerViewAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView contentView;
        private ShoppingListSummary item;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            contentView = view.findViewById(R.id.shopping_content);

            view.setOnClickListener(v -> {
                Log.i(TAG, "Navigating to shopping list with id " + item.getId());
                Navigation.findNavController(v).navigate(ShoppingListFragmentDirections.actionNavShoppingToShoppingDetailFragment(item.getId()));
            });
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }

        public void setItem(ShoppingListSummary item) {
            this.item = item;
        }
    }
}
