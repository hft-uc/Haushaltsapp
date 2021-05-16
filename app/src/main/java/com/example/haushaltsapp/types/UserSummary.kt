package com.example.haushaltsapp.types

data class UserSummary(
        val id: String,
        var name: String,
        var imageUrl: String,
        var status: String
) {
    @Suppress("unused")
    constructor() : this("", "", "", "")

    constructor(id: String, name: String) : this(id, name, "","")
}