package com.example.haushaltsapp.shopping;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

public class ShoppingRepository {

    public static final String SHOPPING_LISTS_COLLECTION = "shopping_lists";
    private static final String TAG = ShoppingRepository.class.getCanonicalName();

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Query getShoppingListsQuery(String userId) {
        return db.collection(AuthRepository.USERS_COLLECTION)
            .document(userId)
            .collection(SHOPPING_LISTS_COLLECTION)
            .orderBy("name");
    }

    public LiveData<ShoppingListDetail> getShoppingList(String id) {
        MutableLiveData<ShoppingListDetail> result = new MutableLiveData<>();

        db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id)
            .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        result.setValue(value.toObject(ShoppingListDetail.class));
                    } else {
                        Log.w(TAG, "getShoppingList failed", error);
                    }
                }
            );

        return result;
    }

    public LiveData<Boolean> addShoppingList(ShoppingListDetail shoppingListDetail, String userId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        WriteBatch batch = db.batch();

        DocumentReference generalRef = db.collection(SHOPPING_LISTS_COLLECTION).document();
        shoppingListDetail.setId(generalRef.getId());
        batch.set(generalRef, shoppingListDetail);

        DocumentReference userSpecificRef = db.collection(AuthRepository.USERS_COLLECTION)
            .document(userId)
            .collection(SHOPPING_LISTS_COLLECTION)
            .document(generalRef.getId());
        batch.set(userSpecificRef, shoppingListDetail.toSummary());

        batch.commit()
            .addOnSuccessListener(value -> result.setValue(true))
            .addOnFailureListener(exception -> result.setValue(false));

        return result;
    }


}
