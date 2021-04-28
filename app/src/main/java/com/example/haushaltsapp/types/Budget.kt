package com.example.haushaltsapp.types

import java.io.Console

data class Budget(
        var id: String,
        val expenditures: MutableList<Expenditure>,
      //  val incomes: MutableList<Income>,
        val owner: UserSummary,
        val members: MutableList<UserSummary>
) {
    @Suppress("unused")
    constructor() : this("", mutableListOf(),  UserSummary(), mutableListOf())
    constructor(name: String, CurrentUser: UserSummary) : this("",mutableListOf(),CurrentUser,mutableListOf(), ) {

    }

    fun toSummary(): BudgetSummary = BudgetSummary(id, "owner")
}