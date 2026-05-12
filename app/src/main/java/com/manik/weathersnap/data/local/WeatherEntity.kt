package com.manik.weathersnap.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey val id: Int? = null,
    val cityName: String,
    val temperature: Double
)
