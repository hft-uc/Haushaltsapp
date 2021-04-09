package com.example.haushaltsapp.types

data class ShoppingListDetail(
        var name: String,
        val entries: MutableList<ShoppingListEntry>,
        val owner: UserSummary,
        val members: MutableList<UserSummary>
) {

    @Suppress("unused")
    constructor() : this("", mutableListOf(), UserSummary(), mutableListOf())

    constructor(name: String, owner: UserSummary) : this(name, mutableListOf(), owner, mutableListOf())

    fun toSummary(): ShoppingListSummary = ShoppingListSummary(name)
}