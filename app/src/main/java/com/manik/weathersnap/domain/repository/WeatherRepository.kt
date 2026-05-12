package com.manik.weathersnap.domain.repository

import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.model.Weather

interface WeatherRepository {
    suspend fun searchCity(name: String): Result<List<City>>
    suspend fun getWeatherData(lat: Double, lon: Double): Result<Weather>
}
