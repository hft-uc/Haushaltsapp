package com.example.haushaltsapp.user;

import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.UserSummary;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

import static com.example.haushaltsapp.authentification.AuthRepository.USERS_COLLECTION;
import static com.example.haushaltsapp.shopping.ShoppingRepository.SHOPPING_LISTS_COLLECTION;

public class UserRepository {

    public static final String MEMBERS_COLLECTION = "members";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * @return Query over collection of {@link com.example.haushaltsapp.types.UserSummary}
     */
    public Query getShoppingListMembers(String id) {
        return db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id)
            .collection(MEMBERS_COLLECTION)
            .orderBy("name");
    }

    public Query getAllUsers() {
        return db.collection(USERS_COLLECTION)
            .orderBy("name");
    }

    public Task<Void> addShoppingListMember(ShoppingListDetail shoppingList, UserSummary user) {
        WriteBatch batch = db.batch();

        DocumentReference generalRef = db.collection(SHOPPING_LISTS_COLLECTION)
            .document(shoppingList.getId())
            .collection(MEMBERS_COLLECTION)
            .document(user.getId());
        batch.set(generalRef, user);

        DocumentReference userSpecificRef = db.collection(USERS_COLLECTION)
            .document(user.getId())
            .collection(SHOPPING_LISTS_COLLECTION)
            .document(shoppingList.getId());
        batch.set(userSpecificRef, shoppingList.toSummary());

        return batch.commit();
    }

    public Task<Void> removeShoppingListMember(ShoppingListDetail shoppingList, UserSummary user) {
        WriteBatch batch = db.batch();

        DocumentReference generalRef = db.collection(SHOPPING_LISTS_COLLECTION)
            .document(shoppingList.getId())
            .collection(MEMBERS_COLLECTION)
            .document(user.getId());
        batch.delete(generalRef);

        DocumentReference userSpecificRef = db.collection(USERS_COLLECTION)
            .document(user.getId())
            .collection(SHOPPING_LISTS_COLLECTION)
            .document(shoppingList.getId());
        batch.delete(userSpecificRef);

        return batch.commit();
    }
}
