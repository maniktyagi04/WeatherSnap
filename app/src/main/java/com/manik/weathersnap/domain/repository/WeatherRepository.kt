package com.manik.weathersnap.domain.repository

import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.model.Weather

interface WeatherRepository {
    suspend fun searchCity(name: String): Result<List<City>>
    suspend fun getWeatherData(lat: Double, lon: Double): Result<Weather>
    suspend fun saveReport(report: com.manik.weathersnap.data.local.ReportEntity)
    fun getAllReports(): kotlinx.coroutines.flow.Flow<List<com.manik.weathersnap.data.local.ReportEntity>>
}
