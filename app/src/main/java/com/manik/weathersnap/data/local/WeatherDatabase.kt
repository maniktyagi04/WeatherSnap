package com.manik.weathersnap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ReportEntity::class],
    version = 4,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val reportDao: ReportDao
}
