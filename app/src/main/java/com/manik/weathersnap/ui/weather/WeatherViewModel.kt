package com.manik.weathersnap.ui.weather

import androidx.lifecycle.ViewModel
import com.manik.weathersnap.data.remote.WeatherApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val apiService: WeatherApiService
) : ViewModel() {
    // Temporary test state
    val testString = "Hilt Injection Successful!"
}
