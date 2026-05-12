package com.manik.weathersnap.ui.weather

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: WeatherViewModel,
    onNavigateToWeather: () -> Unit
) {
    val state by viewModel.searchUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Search City") },
            modifier = Modifier.fillMaxWidth()
        )

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(state.suggestions) { city ->
                ListItem(
                    headlineContent = { Text(city.name) },
                    supportingContent = { Text("${city.admin1 ?: ""}, ${city.country ?: ""}") },
                    modifier = Modifier.clickable {
                        viewModel.selectCity(city)
                        onNavigateToWeather()
                    }
                )
            }
        }
    }
}
