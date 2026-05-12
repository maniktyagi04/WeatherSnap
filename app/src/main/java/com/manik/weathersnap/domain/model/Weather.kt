package com.manik.weathersnap.domain.model

data class Weather(
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Double,
    val weatherCondition: String,
    val weatherCode: Int
)
