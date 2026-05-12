package com.manik.weathersnap.ui.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.weatherUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val uiState = state) {
            is WeatherUiState.Loading -> CircularProgressIndicator()
            is WeatherUiState.Error -> Text("Error: ${uiState.message}", color = MaterialTheme.colorScheme.error)
            is WeatherUiState.Success -> {
                val weather = uiState.weather
                val city = uiState.city
                Text(text = city.name, style = MaterialTheme.typography.headlineLarge)
                Text(text = "${city.admin1 ?: ""}, ${city.country ?: ""}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "${weather.temperature}°C", style = MaterialTheme.typography.displayLarge)
                Text(text = weather.weatherCondition, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    WeatherInfoItem("Humidity", "${weather.humidity}%")
                    WeatherInfoItem("Wind", "${weather.windSpeed} km/h")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    WeatherInfoItem("Pressure", "${weather.pressure} hPa")
                }
            }
            else -> Text("Select a city first")
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBack) {
            Text("Back to Search")
        }
    }
}

@Composable
fun WeatherInfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
