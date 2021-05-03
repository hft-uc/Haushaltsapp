package com.example.haushaltsapp.types

data class ShoppingListDetail(
        var id: String,
        var name: String,
        val owner: UserSummary,
) {

    @Suppress("unused")
    constructor() : this("", "", UserSummary())

    constructor(name: String, owner: UserSummary) : this("", name, owner)

    fun toSummary(): ShoppingListSummary = ShoppingListSummary(id, name)
}