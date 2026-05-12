package com.manik.weathersnap.data.repository

import com.manik.weathersnap.data.local.ReportDao
import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.data.remote.GeocodingApiService
import com.manik.weathersnap.data.remote.WeatherApiService
import com.manik.weathersnap.data.remote.mapper.toCity
import com.manik.weathersnap.data.remote.mapper.toWeather
import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.model.Weather
import com.manik.weathersnap.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [WeatherRepository] that communicates with network APIs 
 * and local database via [ReportDao].
 */
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApiService,
    private val geocodingApi: GeocodingApiService,
    private val reportDao: ReportDao
) : WeatherRepository {

    override suspend fun searchCity(query: String): Result<List<City>> = withContext(Dispatchers.IO) {
        try {
            val response = geocodingApi.searchCity(query)
            val cities = response.results?.map { it.toCity() } ?: emptyList()
            Result.success(cities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWeather(latitude: Double, longitude: Double): Result<Weather> = withContext(Dispatchers.IO) {
        try {
            val response = weatherApi.getWeatherData(latitude, longitude)
            Result.success(response.toWeather())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveReport(report: ReportEntity) {
        withContext(Dispatchers.IO) {
            reportDao.insertReport(report)
        }
    }

    override fun getAllReports(): Flow<List<ReportEntity>> {
        return reportDao.getAllReports()
    }
}
