package com.example.haushaltsapp.types

data class Supply(
        val id: Int,
        val entries: MutableList<SupplyEntry>,
        val owner: User,
        val members: MutableList<User>
) {
    @Suppress("unused")
    constructor() : this(0, mutableListOf(), User(), mutableListOf())
}