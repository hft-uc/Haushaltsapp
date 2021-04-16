package com.example.haushaltsapp.types

data class ShoppingListSummary(
        var id: String,
        var name: String
) {
    @Suppress("unused")
    constructor() : this("", "")
}