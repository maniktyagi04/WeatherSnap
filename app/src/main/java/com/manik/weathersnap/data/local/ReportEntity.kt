package com.manik.weathersnap.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String,
    val temperature: String,
    val humidity: String,
    val windSpeed: String,
    val pressure: String,
    val condition: String,
    val notes: String,
    val imagePath: String,
    val originalImageSize: String,
    val compressedImageSize: String,
    val timestamp: Long
)
