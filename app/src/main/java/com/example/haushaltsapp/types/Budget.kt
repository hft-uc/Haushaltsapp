package com.example.haushaltsapp.types

data class Budget(
        val id: Int,
        val expenditures: MutableList<Expenditure>,
        val incomes: MutableList<Income>,
        val owner: UserSummary,
        val members: MutableList<UserSummary>
) {
    @Suppress("unused")
    constructor() : this(0, mutableListOf(), mutableListOf(), UserSummary(), mutableListOf())
}