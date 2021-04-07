package com.example.haushaltsapp.types

import java.time.ZonedDateTime

data class SupplyEntry(
        val id: Int,
        var name: String,
        var amount: String,
        var expiry: ZonedDateTime?
) {
    @Suppress("unused")
    constructor() : this(0, "", "", null)
}