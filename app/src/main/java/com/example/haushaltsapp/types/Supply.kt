package com.example.haushaltsapp.types

data class Supply(
        val id: Int,
        val entries: MutableList<SupplyEntry>,
        val owner: UserSummary,
        val members: MutableList<UserSummary>
) {
    @Suppress("unused")
    constructor() : this(0, mutableListOf(), UserSummary(), mutableListOf())
}