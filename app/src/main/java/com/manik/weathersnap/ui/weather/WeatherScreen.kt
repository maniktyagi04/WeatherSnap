package com.manik.weathersnap.ui.weather

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.manik.weathersnap.ui.weather.components.SuggestionList
import com.manik.weathersnap.ui.weather.components.WeatherCard
import com.manik.weathersnap.ui.weather.components.WeatherSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onNavigateToReports: () -> Unit,
    onCreateReport: () -> Unit
) {
    val searchState by viewModel.searchUiState.collectAsState()
    val weatherState by viewModel.weatherUiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Column {
                WeatherSearchBar(
                    query = searchState.query,
                    onQueryChange = { viewModel.onSearchQueryChange(it) },
                    onClearQuery = { viewModel.onSearchQueryChange("") }
                )
                if (searchState.query.length in 1..2) {
                    Text(
                        text = "Enter more than 2 letters to start city suggestions",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 32.dp, bottom = 8.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        floatingActionButton = {
            if (weatherState is WeatherUiState.Success) {
                ExtendedFloatingActionButton(
                    onClick = onCreateReport,
                    icon = { Icon(Icons.Default.Add, null) },
                    text = { Text("Create Report") },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Main Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Crossfade(
                    targetState = weatherState,
                    label = "WeatherStateCrossfade"
                ) { state ->
                    when (state) {
                        is WeatherUiState.Idle -> {
                            EmptyStateView("Search for a city to see weather data")
                        }
                        is WeatherUiState.Loading -> {
                            LoadingView()
                        }
                        is WeatherUiState.Error -> {
                            ErrorView(state.message)
                        }
                        is WeatherUiState.Success -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                WeatherCard(
                                    weather = state.weather,
                                    city = state.city
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = onNavigateToReports,
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Icon(Icons.Default.List, null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("View Reports")
                                }
                            }
                        }
                    }
                }
            }

            // Suggestions Dropdown (Overlay)
            SuggestionList(
                suggestions = searchState.suggestions,
                isVisible = searchState.query.length >= 2,
                onCitySelected = { city ->
                    viewModel.selectCity(city)
                    viewModel.onSearchQueryChange("") // Clear search after selection
                },
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Oops!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun EmptyStateView(message: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
