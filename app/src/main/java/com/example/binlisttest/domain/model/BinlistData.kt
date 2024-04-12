package com.example.binlisttest.domain.model

data class BinlistData(
    val request: String = "",
    val number: Number? = Number(),
    val scheme: String? = null,
    val type: String? = null,
    val brand: String? = null,
    val prepaid: Boolean? = null,
    val country: Country? = Country(),
    val bank: Bank? = Bank()
)

data class Number(
    val length: Int? = null,
    val luhn: Boolean? = null
)

data class Country(
    val numeric: String? = null,
    val alpha2: String? = null,
    val name: String? = null,
    val emoji: String? = null,
    val currency: String? = null,
    val latitude: Int? = null,
    val longitude: Int? = null
)

data class Bank(
    val name: String? = null,
    val url: String? = null,
    val phone: String? = null,
    val city: String? = null
)