package com.example.haushaltsapp.types

import com.google.firebase.Timestamp

//type will probably be a enum later
data class Transaction(
        var id: String,
        var amount: Double,
        var name: String,
        var date: Timestamp?,
        var type: String
) {


    @Suppress("unused")
    constructor() : this("", 0.0, "", null, "")
    constructor(name: String, amount: Double) : this("", amount, name, null, "") {

    }
}