package com.example.haushaltsapp.types

data class UserDetail(
        val id: String,
        var name: String,
        var email: String
) {
    @Suppress("unused")
    constructor() : this("", "", "")

    fun toSummary(): UserSummary = UserSummary(id, name)
}