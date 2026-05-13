package com.manik.weathersnap.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a weather report saved by the user.
 * Includes weather metrics, captured image path, and soft delete metadata.
 */
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
    val timestamp: Long,
    
    // Advanced Management Fields
    val isDeleted: Boolean = false,
    val deletedAt: Long? = null
)
