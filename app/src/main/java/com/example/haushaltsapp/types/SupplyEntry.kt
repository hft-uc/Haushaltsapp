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


}