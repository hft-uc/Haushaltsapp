package com.example.haushaltsapp.supply;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.supply.SupplyRecyclerViewAdapter;
import com.example.haushaltsapp.supply.SupplyRepository;
import com.example.haushaltsapp.types.Supply;
import com.example.haushaltsapp.types.SupplyEntry;
import com.example.haushaltsapp.types.SupplySummary;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;


public class SupplyViewModel extends ViewModel {
    private static final String TAG = com.example.haushaltsapp.supply.SupplyViewModel
            .class.getCanonicalName();

    private final SupplyRepository repository = new SupplyRepository();
    private final AuthRepository authRepository = new AuthRepository();

    private MutableLiveData<Supply> supplyLiveData;
    private Supply id;


    public void loadSupply(Supply id) {
        this.id = id;

        supplyLiveData = new MutableLiveData<>();

        repository.getSupply(id)
                .addSnapshotListener((value, error) -> {
                            if (value != null) {
                                supplyLiveData.setValue(value.toObject(Supply.class));
                            } else {
                                Log.w(TAG, "loadSupply failed", error);
                            }
                        }
                );
    }

    public LiveData<Supply> getShoppingList() {
        return supplyLiveData;
    }

    public LiveData<Boolean> add(String name) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        Supply supply = new Supply(name, authRepository.getCurrentUser());
        //repository.addSupply(supply, supply.getOwner().getId())
        //      .addOnCompleteListener(task -> result.setValue(task.isSuccessful()));

        return result;
    }

    public LiveData<Boolean> addEntry(String name, String amount) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        SupplyEntry entry = new SupplyEntry(name, amount);
        repository.addSupply(id, entry)
                .addOnCompleteListener(task -> result.setValue(task.isSuccessful()));

        return result;
    }

    public void update(Supply supply) {
        throw new UnsupportedOperationException();
    }

    public void delete(Supply supply) {
        throw new UnsupportedOperationException();
    }

    public SupplyRecyclerViewAdapter createSupplyAdapter(LifecycleOwner lifecycleOwner) {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(10)
                .setPageSize(20)
                .build();

        final Query query = repository.getSupply(authRepository.getCurrentUser().getId());

        FirestorePagingOptions<SupplySummary> options
                = new FirestorePagingOptions.Builder<SupplySummary>()
                .setLifecycleOwner(lifecycleOwner)
                .setQuery(query, config, SupplySummary.class)
                .build();

        return new SupplyRecyclerViewAdapter(options);
    }
}



