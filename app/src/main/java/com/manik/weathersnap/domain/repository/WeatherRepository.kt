package com.manik.weathersnap.domain.repository

import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.model.Weather
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing weather-related data and local reports.
 * 
 * This repository abstracts the data sources (API and local database) 
 * and provides a unified interface for the domain layer.
 */
interface WeatherRepository {
    /**
     * Searches for cities matching the given [query].
     * @return Result containing a list of [City] objects.
     */
    suspend fun searchCity(query: String): Result<List<City>>

    /**
     * Fetches current weather for the specified [latitude] and [longitude].
     * @return Result containing a [Weather] object.
     */
    suspend fun getWeather(latitude: Double, longitude: Double): Result<Weather>

    /**
     * Persists a new weather report to the local database.
     */
    suspend fun saveReport(report: ReportEntity)

    /**
     * Streams all saved weather reports from the local database.
     * @return A Flow of lists containing [ReportEntity] objects.
     */
    fun getAllReports(): Flow<List<ReportEntity>>
}
