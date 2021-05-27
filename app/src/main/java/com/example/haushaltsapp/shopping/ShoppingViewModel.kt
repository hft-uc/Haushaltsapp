package com.example.haushaltsapp.shopping

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haushaltsapp.authentification.AuthRepository
import com.example.haushaltsapp.types.ShoppingListDetail
import com.example.haushaltsapp.types.ShoppingListEntry
import com.example.haushaltsapp.types.ShoppingListSummary
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 *
 *
 * Contains the state for the fragment.
 *
 *
 *
 *
 * When working with a single shopping list always call [.loadShoppingList] first to initiate
 * the view model. After this all operations on a specific shopping list are operating on that one.
 *
 */
class ShoppingViewModel : ViewModel() {
    private val repository = ShoppingRepository()
    private val authRepository = AuthRepository()
    private lateinit var shoppingListDetailLiveData: MutableLiveData<ShoppingListDetail>
    private lateinit var id: String

    /**
     * Starts to load the `ShoppingListDetail` with given id.
     * It can be queried with `getShoppingList`
     */
    fun loadShoppingList(id: String) {
        this.id = id
        shoppingListDetailLiveData = MutableLiveData()
        repository.getShoppingList(id)
            .addSnapshotListener { value: DocumentSnapshot?, error: FirebaseFirestoreException? ->
                if (value != null) {
                    shoppingListDetailLiveData.setValue(value.toObject(
                        ShoppingListDetail::class.java))
                } else {
                    Log.w(TAG, "loadShoppingList failed", error)
                }
            }
    }

    val shoppingList: LiveData<ShoppingListDetail>
        get() = shoppingListDetailLiveData

    fun add(name: String?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val shoppingList = ShoppingListDetail(name!!, authRepository.currentUser)
        repository.addShoppingList(shoppingList, shoppingList.owner.id)
            .addOnCompleteListener { task: Task<Void?> -> result.setValue(task.isSuccessful) }
        return result
    }

    fun addEntry(name: String?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val entry = ShoppingListEntry(name!!)
        repository.addShoppingListEntry(id, entry)
            .addOnCompleteListener { task: Task<Void?> -> result.setValue(task.isSuccessful) }
        return result
    }

    fun update(shoppingListDetail: ShoppingListDetail) {
        throw UnsupportedOperationException()
    }

    fun delete(shoppingListDetail: ShoppingListDetail) {
        throw UnsupportedOperationException()
    }

    fun toggleDone(item: ShoppingListEntry) {
        item.isDone = !item.isDone
        Log.d(TAG, "Toggling is done to '" + item.isDone + "' for item '" + item.id + "'")
        repository.updateEntry(id, item)
    }

    fun createShoppingListAdapter(lifecycleOwner: LifecycleOwner): ShoppingRecyclerViewAdapter {
        val query = repository.getShoppingLists(authRepository.currentUser.id)
        val options = FirestoreRecyclerOptions.Builder<ShoppingListSummary>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(query, ShoppingListSummary::class.java)
            .build()
        return ShoppingRecyclerViewAdapter(options)
    }

    fun createShoppingListEntriesAdapter(lifecycleOwner: LifecycleOwner): FirestoreRecyclerOptions<ShoppingListEntry> {
        val query = repository.getShoppingListEntries(id)
        return FirestoreRecyclerOptions.Builder<ShoppingListEntry>()
            .setQuery(query, ShoppingListEntry::class.java)
            .setLifecycleOwner(lifecycleOwner)
            .build()
    }

    fun clearShoppingList() {
        Log.d(TAG, "Deleting entries of shopping list with id '$id'")
        repository.deleteEntries(id)
    }

    companion object {
        private val TAG = ShoppingViewModel::class.java.canonicalName
    }
}