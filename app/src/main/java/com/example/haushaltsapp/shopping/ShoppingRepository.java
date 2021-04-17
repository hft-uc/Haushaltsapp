package com.example.haushaltsapp.shopping;

import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.ShoppingListEntry;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

public class ShoppingRepository {

    public static final String SHOPPING_LISTS_COLLECTION = "shopping_lists";
    public static final String SHOPPING_LISTS_ENTRY_COLLECTION = "entry";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * @return Query over collection of {@link com.example.haushaltsapp.types.ShoppingListSummary}
     */
    public Query getShoppingLists(String userId) {
        return db.collection(AuthRepository.USERS_COLLECTION)
            .document(userId)
            .collection(SHOPPING_LISTS_COLLECTION)
            .orderBy("name");
    }

    /**
     * @return Query over document of {@link com.example.haushaltsapp.types.ShoppingListDetail}
     */
    public DocumentReference getShoppingList(String id) {
        return db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id);
    }

    public Task<Void> addShoppingList(ShoppingListDetail shoppingListDetail, String userId) {
        WriteBatch batch = db.batch();

        DocumentReference generalRef = db.collection(SHOPPING_LISTS_COLLECTION).document();
        shoppingListDetail.setId(generalRef.getId());
        batch.set(generalRef, shoppingListDetail);

        DocumentReference userSpecificRef = db.collection(AuthRepository.USERS_COLLECTION)
            .document(userId)
            .collection(SHOPPING_LISTS_COLLECTION)
            .document(generalRef.getId());
        batch.set(userSpecificRef, shoppingListDetail.toSummary());

        return batch.commit();
    }

    /**
     * @param id The id of the shopping list to add to
     */
    public Task<Void> addShoppingListEntry(String id, ShoppingListEntry entry) {
        final DocumentReference reference = db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id)
            .collection(SHOPPING_LISTS_ENTRY_COLLECTION)
            .document();
        entry.setId(reference.getId());

        return reference
            .set(entry);

    }
}
