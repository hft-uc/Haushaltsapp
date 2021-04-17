package com.example.haushaltsapp.user;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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

}
