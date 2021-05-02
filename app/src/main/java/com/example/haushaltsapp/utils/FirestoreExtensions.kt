package com.example.haushaltsapp.utils

import com.google.firebase.firestore.DocumentSnapshot

fun <T> List<DocumentSnapshot>.toObjectList(clazz: Class<T>): List<T> {
    return this.mapNotNull { it.toObject(clazz) }
}