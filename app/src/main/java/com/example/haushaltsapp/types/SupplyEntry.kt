package com.example.haushaltsapp.types

import java.time.ZonedDateTime

data class SupplyEntry(
        //val id: Int,
        var id: String,
        var name: String,
        var amount: String,
        var expiry: ZonedDateTime?
) {
    @Suppress("unused")
    constructor() : this("", "", "", null)
    constructor(amount: String) : this() {

    }

    constructor(name: String, amount: String) : this("", "", "", null) {

    }



    fun toSummary(): SupplySummary = SupplySummary(id, name)
}