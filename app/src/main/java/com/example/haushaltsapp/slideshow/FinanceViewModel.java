package com.example.haushaltsapp.slideshow;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.Budget;
import com.example.haushaltsapp.types.BudgetSummary;
import com.example.haushaltsapp.types.Expenditure;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;

public class FinanceViewModel extends ViewModel {
    private static final String TAG = "FinanceViewModel";
    private final AuthRepository authRepository = new AuthRepository();

    private final BudgetRepository repository = new BudgetRepository();

    private MutableLiveData<Budget> BudgetLiveData;
    private String id;

    public void loadBudget(String id){
        this.id = id;
        BudgetLiveData = new MutableLiveData<>();
        repository.getBudget(id)
                .addSnapshotListener((value, error) -> {
                            if (value != null) {
                                BudgetLiveData.setValue(value.toObject(Budget.class));
                            } else {
                                Log.w(TAG, "loadBudget failed", error);
                            }
                        }
                );
    }


    public LiveData<Budget> getBudgetsList() {
        return BudgetLiveData;
    }


    public LiveData<Boolean> add(String name) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        Budget budget = new Budget(name, authRepository.getCurrentUser());
        repository.addBudget(budget, budget.getOwner().getId())
                .addOnCompleteListener(task -> result.setValue(task.isSuccessful()));

        return result;
    }


    public LiveData<Boolean> addEntry(String name, Double amount) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        Expenditure entry = new Expenditure(name, amount);
        repository.addBudgetTransaction(id, entry)
                .addOnCompleteListener(task -> result.setValue(task.isSuccessful()));
        return result;

    }

    public BudgetRecyclerviewAdapter createBudgetAdapter(LifecycleOwner lifecycleOwner) {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(10)
                .setPageSize(20)
                .build();

        final Query query = repository.getBudgets(authRepository.getCurrentUser().getId());

        FirestorePagingOptions<BudgetSummary> options
                = new FirestorePagingOptions.Builder<BudgetSummary>()
                .setLifecycleOwner(lifecycleOwner)
                .setQuery(query, config, BudgetSummary.class)
                .build();

        return new BudgetRecyclerviewAdapter(options);
    }


}