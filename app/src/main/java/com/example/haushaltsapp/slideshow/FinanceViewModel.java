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
import com.example.haushaltsapp.types.Transaction;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;

import java.util.Map;

public class FinanceViewModel extends ViewModel {
    private static final String TAG = "FinanceViewModel";
    private final AuthRepository authRepository = new AuthRepository();

    private final BudgetRepository repository = new BudgetRepository();

    private MutableLiveData<Budget> BudgetLiveData;
    private String id;
    Map<String, Double> transactionMap;

    public Map<String, Double> getTransactionsMap() {
        return transactionMap;
    }

    public String getid() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void loadBudget(String id) {
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

        Transaction entry = new Transaction(name, amount, authRepository.getCurrentUser().getName(), "cat");
        repository.addBudgetTransaction(id, entry)
                .addOnCompleteListener(task -> result.setValue(task.isSuccessful()));
        return result;

    }

    public LiveData<Boolean> addEntryByID(String id1, String name, Double amount, String category) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        String test = authRepository.getCurrentUser().getName();
        Transaction entry = new Transaction(name, amount, test, category);
        repository.addBudgetTransaction(id1, entry)
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

    public FirestoreRecyclerOptions<Transaction> createEntriesAdapter(LifecycleOwner lifecycleOwner) {
        final Query query = repository.getTransactions(id);

        return new FirestoreRecyclerOptions.Builder<Transaction>()
                .setQuery(query, Transaction.class)
                .setLifecycleOwner(lifecycleOwner)
                .build();
    }

    public Map<String, Double> test(String id) {
        transactionMap = repository.test(id);
        return transactionMap;
    }


}