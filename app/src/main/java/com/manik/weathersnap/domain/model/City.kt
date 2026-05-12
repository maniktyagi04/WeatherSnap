package com.manik.weathersnap.domain.model

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?,
    val admin1: String?
)
