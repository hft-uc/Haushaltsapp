package com.example.haushaltsapp.types

data class User(
        val id: String,
        var name: String,
        var email: String
) {
    @Suppress("unused")
    constructor() : this("", "", "")
}