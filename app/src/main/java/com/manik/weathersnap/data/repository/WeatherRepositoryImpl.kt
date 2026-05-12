package com.manik.weathersnap.data.repository

import com.manik.weathersnap.data.remote.GeocodingApiService
import com.manik.weathersnap.data.remote.WeatherApiService
import com.manik.weathersnap.data.remote.mapper.toCity
import com.manik.weathersnap.data.remote.mapper.toWeather
import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.model.Weather
import com.manik.weathersnap.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApiService,
    private val geocodingApi: GeocodingApiService
) : WeatherRepository {

    override suspend fun searchCity(name: String): Result<List<City>> {
        return try {
            val response = geocodingApi.searchCity(name)
            val cities = response.results?.map { it.toCity() } ?: emptyList()
            Result.success(cities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWeatherData(lat: Double, lon: Double): Result<Weather> {
        return try {
            val response = weatherApi.getWeatherData(lat, lon)
            Result.success(response.toWeather())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
