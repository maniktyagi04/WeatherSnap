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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import com.manik.weathersnap.ui.weather.components.SuggestionList
import com.manik.weathersnap.ui.weather.components.WeatherCard
import com.manik.weathersnap.ui.weather.components.WeatherSearchBar
import com.manik.weathersnap.ui.theme.SkyBlue
import com.manik.weathersnap.ui.theme.MidnightBlue
import com.manik.weathersnap.ui.theme.DeepIndigo
import com.manik.weathersnap.ui.theme.SoftBlue

import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.filled.DeleteSweep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onNavigateToReports: () -> Unit,
    onCreateReport: () -> Unit,
    onNavigateToTrash: () -> Unit
) {
    val searchState by viewModel.searchUiState.collectAsState()
    val weatherState by viewModel.weatherUiState.collectAsState()
    val scrollState = rememberScrollState()

    // Clear state on back press if we are showing a weather result
    BackHandler(enabled = weatherState !is WeatherUiState.Idle) {
        viewModel.resetToIdle()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MidnightBlue)
                    .padding(top = 12.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WeatherSearchBar(
                        query = searchState.query,
                        onQueryChange = { viewModel.onSearchQueryChange(it) },
                        onClearQuery = { viewModel.onSearchQueryChange("") },
                        modifier = Modifier.weight(1f)
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    IconButton(
                        onClick = onNavigateToTrash,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(14.dp))
                    ) {
                        Icon(Icons.Default.DeleteSweep, "Trash", tint = SkyBlue, modifier = Modifier.size(24.dp))
                    }
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = weatherState is WeatherUiState.Success,
                enter = androidx.compose.animation.scaleIn() + fadeIn(),
                exit = androidx.compose.animation.scaleOut() + fadeOut()
            ) {
                FloatingActionButton(
                    onClick = onCreateReport,
                    containerColor = SkyBlue,
                    contentColor = MidnightBlue,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(Icons.Default.Add, null, modifier = Modifier.size(26.dp))
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(MidnightBlue, DeepIndigo)))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Crossfade(targetState = weatherState, label = "WeatherState") { state ->
                    when (state) {
                        is WeatherUiState.Idle -> EmptyStateView("Enter a city to explore weather")
                        is WeatherUiState.Loading -> LoadingView()
                        is WeatherUiState.Error -> ErrorView(state.message)
                        is WeatherUiState.Success -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(bottom = 60.dp, top = 16.dp)
                            ) {
                                WeatherCard(weather = state.weather, city = state.city)
                                
                                Spacer(modifier = Modifier.height(20.dp))
                                
                                Button(
                                    onClick = onNavigateToReports,
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.05f)),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp)
                                        .height(52.dp)
                                ) {
                                    Icon(Icons.Default.List, null, tint = SkyBlue, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text("VIEW SAVED REPORTS", style = MaterialTheme.typography.labelLarge, color = SkyBlue, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
            
            SuggestionList(
                suggestions = searchState.suggestions,
                isVisible = searchState.query.length >= 2,
                onCitySelected = { city ->
                    viewModel.selectCity(city)
                    viewModel.onSearchQueryChange("") 
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
