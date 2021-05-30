package com.example.haushaltsapp.supply;
import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.Supply;
import com.example.haushaltsapp.types.SupplyEntry;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

public class SupplyRepository {

    public static final String SUPPLY_COLLECTION = "Vorrat";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();



    public Query getSupply(String userId) {
        return db.collection(AuthRepository.USERS_COLLECTION)
                .document(userId)
                .collection(SUPPLY_COLLECTION)
                .orderBy("name");
    }



    public DocumentReference getSupply(Supply id) {
        return db.collection(SUPPLY_COLLECTION)
                .document(String.valueOf(id));
    }

    public Task<Void> addSupply(Supply supplyEntry, SupplyEntry userId) {
        WriteBatch batch = db.batch();

        DocumentReference generalRef = db.collection(SUPPLY_COLLECTION).document();
        supplyEntry.setId(generalRef.getId());
        batch.set(generalRef, supplyEntry);

        DocumentReference userSpecificRef = db.collection(AuthRepository.USERS_COLLECTION)
                .document(String.valueOf(userId))
                .collection(SUPPLY_COLLECTION)
                .document(generalRef.getId());
        batch.set(userSpecificRef, supplyEntry.toSummary());

        return batch.commit();
    }


    public Task<Void> adSupplyEntry(String id, SupplyEntry entry) {
        final DocumentReference reference = db.collection(SUPPLY_COLLECTION)
                .document(id)
                .collection(SUPPLY_COLLECTION)
                .document();
        entry.setId(reference.getId());

        return reference
                .set(entry);

    }
}
