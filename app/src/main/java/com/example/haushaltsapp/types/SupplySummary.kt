package com.example.haushaltsapp.types

data class SupplySummary(
        var id: String,
        var name: String
) {
    @Suppress("unused")
    constructor() : this("", "")
}

