package com.example.haushaltsapp.user;

import com.example.haushaltsapp.types.UserSummary;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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

    public Query getMembers(String id) {
        return db.collection(MEMBERS_COLLECTION).orderBy("name");
    }

    public Query getAllUsers() {
        return db.collection(USERS_COLLECTION)
            .orderBy("name");
    }

    public Task<Void> addShoppingListMember(String id, UserSummary user) {
        DocumentReference document = db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id)
            .collection(MEMBERS_COLLECTION)
            .document(user.getId());
        return document.set(user);

    }

}
