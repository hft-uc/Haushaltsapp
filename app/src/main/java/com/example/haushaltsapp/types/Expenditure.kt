package com.example.haushaltsapp.types

import java.time.ZonedDateTime
//type will probably be a enum later
data class Expenditure(
        var id: String,
        var amount: Double,
        var name: String,
        var date: ZonedDateTime?,
var type:String
) {


    @Suppress("unused")
    constructor() : this("", 0.0, "", null,"")
    constructor(name: String, amount: Double) : this("",amount,name, null,"") {

    }
}