package com.example.haushaltsapp.types

data class BudgetSummary(
        var id: String,
        var name: String
) {
    @Suppress("unused")
    constructor() : this("", "")
}