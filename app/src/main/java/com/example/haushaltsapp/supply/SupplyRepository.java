package com.example.haushaltsapp.supply;
import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.SupplyEntry;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

public class SupplyRepository {

    public static final String SUPPLY_COLLECTION = "supply";
    public static final String SUPPLY_ENTRY_COLLECTION = "entry";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * @return Query over collection of {@link com.example.haushaltsapp.types.SupplySummary}
     */
    public Query getSupply(String userId) {
        return db.collection(AuthRepository.USERS_COLLECTION)
                .document(userId)
                .collection(SUPPLY_COLLECTION)
                .orderBy("name");
    }

    /**
     * @return Query over document of {@link com.example.haushaltsapp.types.SupplyDetail}
     */
    public DocumentReference getShoppingList(String id) {
        return db.collection(SUPPLY_COLLECTION)
                .document(id);
    }

    /**
     * @param id The id of the shopping list to add to
     */
    public Task<Void> addSupplyEntry(String id, SupplyEntry entry) {
        final DocumentReference reference = db.collection(SUPPLY_COLLECTION)
                .document(id)
                .collection(SUPPLY_ENTRY_COLLECTION)
                .document();
        entry.setId(reference.getId());

        return reference
                .set(entry);

    }
}
