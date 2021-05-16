package com.example.haushaltsapp.types

data class UserDetail(
        val id: String,
        var name: String,
        var email: String,
        var imageUrl: String,
        var status: String
) {
    @Suppress("unused")
    constructor() : this("", "", "", "", "");

    constructor(id: String, name: String, email: String) : this(id, name, email, "","")

    fun toSummary(): UserSummary = UserSummary(id, name)

    fun toChatUserSummary(): UserSummary = UserSummary(id, name, imageUrl, status)
}