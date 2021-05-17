package com.example.haushaltsapp.utils

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot

fun <T> List<DocumentSnapshot>.toObjectList(clazz: Class<T>): List<T> {
    return this.mapNotNull { it.toObject(clazz) }
}

/**
 * From https://stackoverflow.com/a/52142187/13516981
 */
fun CollectionReference.delete(batchSize: Int, tag: String) {
    try {
        // Retrieve a small batch of documents to avoid out-of-memory errors/
        var deleted = 0
        this
            .limit(batchSize.toLong())
            .get()
            .addOnCompleteListener { task ->
                task.result?.let { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        document.reference.delete()
                        ++deleted
                    }
                    if (deleted >= batchSize) {
                        // retrieve and delete another batch
                        this.delete(batchSize, tag)
                    }
                }

            }
    } catch (e: Exception) {
        Log.e(tag, "Error deleting collection : " + e.message)
    }
}