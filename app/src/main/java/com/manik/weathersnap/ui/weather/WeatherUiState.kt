package com.manik.weathersnap.ui.weather

import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.model.Weather

sealed class WeatherUiState {
    data object Idle : WeatherUiState()
    data object Loading : WeatherUiState()
    data class Success(val weather: Weather, val city: City) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

data class SearchUiState(
    val query: String = "",
    val suggestions: List<City> = emptyList(),
    val isLoading: Boolean = false
)
