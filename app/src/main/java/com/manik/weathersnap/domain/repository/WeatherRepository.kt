package com.manik.weathersnap.domain.repository

import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.model.Weather
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing weather-related data and local reports.
 */
interface WeatherRepository {
    suspend fun searchCity(query: String): Result<List<City>>
    suspend fun getWeather(latitude: Double, longitude: Double): Result<Weather>
    
    // Report Management
    suspend fun saveReport(report: ReportEntity)
    suspend fun updateReport(report: ReportEntity)
    fun getActiveReports(): Flow<List<ReportEntity>>
    fun getTrashReports(): Flow<List<ReportEntity>>
    suspend fun getReportById(id: Int): ReportEntity?
    suspend fun softDeleteReport(id: Int)
    suspend fun restoreReport(id: Int)
    suspend fun permanentlyDeleteReport(report: ReportEntity)
}
