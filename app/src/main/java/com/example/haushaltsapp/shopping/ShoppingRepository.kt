package com.example.haushaltsapp.shopping

import com.example.haushaltsapp.authentification.AuthRepository
import com.example.haushaltsapp.types.ShoppingListDetail
import com.example.haushaltsapp.types.ShoppingListEntry
import com.example.haushaltsapp.utils.delete
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ShoppingRepository {
    private val db = FirebaseFirestore.getInstance()

    /**
     * @return Query over collection of [com.example.haushaltsapp.types.ShoppingListSummary]
     */
    fun getShoppingLists(userId: String?): Query {
        return db.collection(AuthRepository.USERS_COLLECTION)
            .document(userId!!)
            .collection(SHOPPING_LISTS_COLLECTION)
            .orderBy("name")
    }

    /**
     * @return Query over document of [com.example.haushaltsapp.types.ShoppingListDetail]
     */
    fun getShoppingList(id: String?): DocumentReference {
        return db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id!!)
    }

    fun addShoppingList(shoppingListDetail: ShoppingListDetail, userId: String?): Task<Void> {
        val batch = db.batch()
        val generalRef = db.collection(SHOPPING_LISTS_COLLECTION).document()
        shoppingListDetail.id = generalRef.id
        batch[generalRef] = shoppingListDetail
        val userSpecificRef = db.collection(AuthRepository.USERS_COLLECTION)
            .document(userId!!)
            .collection(SHOPPING_LISTS_COLLECTION)
            .document(generalRef.id)
        batch[userSpecificRef] = shoppingListDetail.toSummary()
        return batch.commit()
    }

    fun getShoppingListEntries(id: String?): Query {
        return db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id!!)
            .collection(SHOPPING_LIST_ENTRIES_COLLECTION)
            .orderBy("done")
            .orderBy("name")
    }

    /**
     * @param id The id of the shopping list to add to
     */
    fun addShoppingListEntry(id: String?, entry: ShoppingListEntry): Task<Void> {
        val reference = db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id!!)
            .collection(SHOPPING_LIST_ENTRIES_COLLECTION)
            .document()
        entry.id = reference.id
        return reference
            .set(entry)
    }

    fun updateEntry(id: String?, entry: ShoppingListEntry): Task<Void> {
        return db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id!!)
            .collection(SHOPPING_LIST_ENTRIES_COLLECTION)
            .document(entry.id)
            .set(entry)
    }

    fun deleteEntries(id: String?) {
        db.collection(SHOPPING_LISTS_COLLECTION)
            .document(id!!)
            .collection(SHOPPING_LIST_ENTRIES_COLLECTION)
            .delete(10, TAG)
    }

    companion object {
        private val TAG = ShoppingRepository::class.java.canonicalName

        const val SHOPPING_LISTS_COLLECTION = "shopping_lists"
        const val SHOPPING_LIST_ENTRIES_COLLECTION = "entries"
    }
}