package com.example.haushaltsapp.types

data class Supply(
        var id: String,
        var name: String,
        val entries: MutableList<SupplyEntry>,
        val owner: UserSummary,
        val members: MutableList<UserSummary>
) {
    // @Suppress("unused")
    //constructor() : this(0, mutableListOf(), UserSummary(), mutableListOf())

    @Suppress("unused")
    constructor() : this("", "", mutableListOf(), UserSummary(), mutableListOf())

    constructor(name: String, owner: UserSummary) : this("", name, mutableListOf(), owner, mutableListOf())

    fun toSummary(): ShoppingListSummary = ShoppingListSummary(id, name)

}
