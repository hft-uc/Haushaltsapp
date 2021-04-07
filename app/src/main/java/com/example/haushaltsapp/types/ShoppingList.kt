package com.example.haushaltsapp.types

data class ShoppingList(
        val id: Int,
        var name: String,
        val entries: MutableList<ShoppingListEntry>,
        val owner: User,
        val members: MutableList<User>
) {

    @Suppress("unused")
    constructor() : this(0, "", mutableListOf(), User(), mutableListOf())

    constructor(name: String, owner: User) : this(0, name, mutableListOf(), owner, mutableListOf())
}