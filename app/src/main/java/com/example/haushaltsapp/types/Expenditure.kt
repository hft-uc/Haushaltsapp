package com.example.haushaltsapp.types

import java.time.ZonedDateTime

data class Expenditure(
        var id: String,
        var amount: Double,
        var name: String,
        var date: ZonedDateTime?
) {


    @Suppress("unused")
    constructor() : this("", 0.0, "", null)
}