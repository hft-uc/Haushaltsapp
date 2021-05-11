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
    constructor(name: String, CurrentUser: UserSummary) : this("", mutableListOf(), CurrentUser, mutableListOf(), 0.0, name) {

    }

    fun toSummary(): BudgetSummary = BudgetSummary(id, owner.toString())
}