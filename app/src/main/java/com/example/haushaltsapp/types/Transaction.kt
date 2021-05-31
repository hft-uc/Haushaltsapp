package com.example.haushaltsapp.types

//type will probably be a enum later
data class Transaction(
        var id: String,
        var amount: Double,
        var name: String,
        var date: String?,
        var type: String,
        var owner: String,
        var category: String
) {


    @Suppress("unused")
    constructor() : this("", 0.0, "", "", "", "", "")
    constructor(name: String, amount: Double, owner: String, category: String, date: String) : this("", amount, name, date, "", owner, category) {
    }

    companion object {
        lateinit var category: String
    }
}