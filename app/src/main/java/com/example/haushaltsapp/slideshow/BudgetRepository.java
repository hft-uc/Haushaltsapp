package com.example.haushaltsapp.slideshow;

import com.example.haushaltsapp.types.Expenditure;
import com.example.haushaltsapp.types.ShoppingListEntry;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BudgetRepository {

    public static final String Budget_COLLECTION = "Budgets";
    public static final String Transactions = "Transactions";


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    public DocumentReference getBudget(String id) {
        return db.collection(Budget_COLLECTION)
                .document(id);
    }


    public Task<Void> addBudgetTransaction(String id, Expenditure entry) {
        final DocumentReference reference = db.collection(Budget_COLLECTION)
                .document(id)
                .collection(Transactions)
                .document();
        entry.setId(reference.getId());

        return reference
                .set(entry);

    }



}
