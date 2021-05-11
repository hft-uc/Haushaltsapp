package com.example.haushaltsapp.types

data class Budget(
        var id: String,
        val Transaction: MutableList<Transaction>,
        //  val incomes: MutableList<Income>,
        val owner: UserSummary,
        val members: MutableList<UserSummary>,
        var sum: Double,
        var name: String
) {
    @Suppress("unused")
    constructor() : this("", mutableListOf(), UserSummary(), mutableListOf(), 0.0, "")
    constructor(name: String, owner: UserSummary) : this("", mutableListOf(), owner, mutableListOf(), 0.0, name) {

    }

    fun toSummary(): BudgetSummary = BudgetSummary(id, owner.toString())
}