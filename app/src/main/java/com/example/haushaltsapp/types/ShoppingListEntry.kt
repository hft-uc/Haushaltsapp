package com.example.haushaltsapp.types

data class ShoppingListEntry(
        var id: String,
        var name: String,
        var isDone: Boolean
) {
    @Suppress("unused")
    constructor() : this("", "", false)

    constructor(name: String) : this("", name, false)
}