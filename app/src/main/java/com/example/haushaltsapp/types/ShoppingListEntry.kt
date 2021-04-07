package com.example.haushaltsapp.types

data class ShoppingListEntry(
        val id: Int,
        var name: String,
        var amount: String,
        var isDone: Boolean
) {
    @Suppress("unused")
    constructor() : this(0, "", "", false)
}