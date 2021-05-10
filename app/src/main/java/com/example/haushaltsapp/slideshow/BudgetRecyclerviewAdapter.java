package com.example.haushaltsapp.slideshow;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.BudgetSummary;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import org.jetbrains.annotations.NotNull;

public class BudgetRecyclerviewAdapter extends FirestorePagingAdapter<BudgetSummary, BudgetRecyclerviewAdapter.ViewHolder> {

    private static final String TAG = BudgetRecyclerviewAdapter.class.getCanonicalName();

    public BudgetRecyclerviewAdapter(@NonNull FirestorePagingOptions<BudgetSummary> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position, @NonNull @NotNull BudgetSummary model) {
        holder.setItem(model);
        holder.contentView.setText(model.getId());
    }


    @NonNull
    @Override
    public BudgetRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_budget_item, parent, false);

        return new BudgetRecyclerviewAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView contentView;
        private BudgetSummary item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            contentView = view.findViewById(R.id.budgetContent);

            view.setOnClickListener(v -> {
                Log.i(TAG, "Navigating to transaction list with id " + item.getId());
                //   Navigation.findNavController(v).navigate();
                Navigation.findNavController(v).navigate(FinanceFragmentDirections.actionNavSlideshowToHistoryFragment(item.getId()));
            });
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }

        public void setItem(BudgetSummary item) {
            this.item = item;
        }
    }


}
