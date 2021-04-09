package com.example.haushaltsapp.types

data class UserSummary(
        val id: String,
        var name: String
) {
    @Suppress("unused")
    constructor() : this("", "")
}