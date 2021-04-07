package com.example.haushaltsapp.types

data class Budget(
        val id: Int,
        val expenditures: MutableList<Expenditure>,
        val incomes: MutableList<Income>,
        val owner: User,
        val members: MutableList<User>
) {
    @Suppress("unused")
    constructor() : this(0, mutableListOf(), mutableListOf(), User(), mutableListOf())
}