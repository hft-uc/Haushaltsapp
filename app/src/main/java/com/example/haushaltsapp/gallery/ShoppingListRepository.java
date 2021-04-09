package com.example.haushaltsapp.gallery;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.haushaltsapp.types.ShoppingList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ShoppingListRepository {

    private static final String SHOPPING_LISTS_COLLECTION = "shopping_lists";
    private static final String USERS_COLLECTION = "users";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public LiveData<List<ShoppingList>> getShoppingListsOf(String userId) {
        MutableLiveData<List<ShoppingList>> result = new MutableLiveData<>();

        db.collection(USERS_COLLECTION)
            .document(userId)
            .collection(SHOPPING_LISTS_COLLECTION)
            .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e(ShoppingListRepository.class.getCanonicalName(),
                            "FirebaseFirestoreException TODO handling", error);
                    } else {
                        result.setValue(toShoppingList(value.iterator()));
                    }
                }
            );

        return result;
    }

    public LiveData<ShoppingList> getShoppingList(String id) {
        MutableLiveData<ShoppingList> result = new MutableLiveData<>();

        db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id)
            .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e(ShoppingListRepository.class.getCanonicalName(),
                            "FirebaseFirestoreException TODO handling", error);
                    } else {
                        result.setValue(value.toObject(ShoppingList.class));
                    }
                }
            );

        return result;
    }

    private List<ShoppingList> toShoppingList(Iterator<QueryDocumentSnapshot> iterator) {
        List<ShoppingList> result = new ArrayList<>();

        while (iterator.hasNext()) {
            QueryDocumentSnapshot doc = iterator.next();

            result.add(doc.toObject(ShoppingList.class));
        }

        return result;
    }

    public LiveData<Boolean> addShoppingList(ShoppingList shoppingList, String userId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        WriteBatch batch = db.batch();

        DocumentReference generalRef = db.collection(SHOPPING_LISTS_COLLECTION).document();
        batch.set(generalRef, shoppingList);

        DocumentReference userSpecificRef = db.collection(USERS_COLLECTION)
            .document(userId)
            .collection(SHOPPING_LISTS_COLLECTION)
            .document(generalRef.getId());
        batch.set(userSpecificRef, shoppingList);

        batch.commit()
            .addOnSuccessListener(command -> result.setValue(true))
            .addOnFailureListener(command -> result.setValue(false));

        return result;
    }


}
