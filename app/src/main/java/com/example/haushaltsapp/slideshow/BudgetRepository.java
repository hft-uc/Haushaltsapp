package com.example.haushaltsapp.slideshow;

import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.Budget;
import com.example.haushaltsapp.types.Transaction;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Query getTransactions(String id) {
        return db.collection(Budget_COLLECTION)
                .document(id)
                .collection(Transactions)
                .orderBy("name");
    }

    public Task<Void> addBudgetTransaction(String id, Transaction entry) {
        final DocumentReference reference = db.collection(Budget_COLLECTION)
                .document(id)
                .collection(Transactions)
                .document();
        entry.setId(reference.getId());
        return reference
                .set(entry);

    }

    public Map<String, Double> test(String id) {
        final DocumentReference reference = db.collection(Budget_COLLECTION).document(id).collection(Transactions).document("x42NyrC6M49HIHsv3FDa");
        Map<String, Double> ret = new HashMap<String, Double>();
        db.collection(Budget_COLLECTION).document(id).collection(Transactions).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < myListOfDocuments.size(); i++) {
                    map = myListOfDocuments.get(i).getData();
                    if (map != null) {
                        String test = (String) map.get("category");
                        Double test1 = (Double) map.get("amount");
                        if (ret.get(test) != null) {
                            Double temp = ret.get(test);
                            ret.put(test, test1 + temp);
                        } else {
                            ret.put(test, test1);
                        }
                    }
                }
            }
        });
        return ret;
    }


}
