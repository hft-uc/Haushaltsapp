package com.example.haushaltsapp.slideshow;

import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.Budget;
import com.example.haushaltsapp.types.Expenditure;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.ShoppingListEntry;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

public class BudgetRepository {

    public static final String Budget_COLLECTION = "Budgets";
    public static final String Transactions = "Transactions";


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    public DocumentReference getBudget(String id) {
        return db.collection(Budget_COLLECTION)
                .document(id);
    }
    public Query getBudgets(String userId) {
        return db.collection(AuthRepository.USERS_COLLECTION)
                .document(userId)
                .collection(Budget_COLLECTION)
                .orderBy("name");
    }


    public Task<Void> addBudget(Budget budget, String userId) {
        WriteBatch batch = db.batch();

        DocumentReference generalRef = db.collection(Budget_COLLECTION).document();
        budget.setId(generalRef.getId());
        batch.set(generalRef, budget);

        DocumentReference userSpecificRef = db.collection(AuthRepository.USERS_COLLECTION)
                .document(userId)
                .collection(Budget_COLLECTION)
                .document(generalRef.getId());
        batch.set(userSpecificRef, budget.toSummary());

        return batch.commit();
    }



    public Task<Void> addBudgetTransaction(String id, Expenditure entry) {
        final DocumentReference reference = db.collection(Budget_COLLECTION)
                .document("KopVcJMWdXTOwRPnHdA2")
                .collection(Transactions)
                .document();

        entry.setId(reference.getId());

        return reference
                .set(entry);



    }



}
