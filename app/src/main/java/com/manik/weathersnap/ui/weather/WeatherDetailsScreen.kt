package com.manik.weathersnap.ui.weather

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manik.weathersnap.ui.theme.*
import com.manik.weathersnap.ui.weather.components.WeatherCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailsScreen(
    viewModel: WeatherViewModel,
    onBack: () -> Unit,
    onCreateReport: () -> Unit
) {
    val weatherState by viewModel.weatherUiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "WEATHER DETAILS", 
                        style = MaterialTheme.typography.titleMedium, 
                        fontWeight = FontWeight.Bold, 
                        letterSpacing = 1.sp,
                        color = TextPrimary
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = AccentBlue)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppBackground
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Crossfade(targetState = weatherState, label = "WeatherStateDetails") { state ->
                    when (state) {
                        is WeatherUiState.Loading -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = AccentBlue)
                            }
                        }
                        is WeatherUiState.Error -> {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(state.message, color = MaterialTheme.colorScheme.error)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = onBack) { Text("Go Back") }
                            }
                        }
                        is WeatherUiState.Success -> {
                            WeatherCard(
                                weather = state.weather,
                                city = state.city,
                                onCreateReport = onCreateReport,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                        else -> {
                            // Should not happen as we navigate here with success
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No data available", color = TextSecondary)
                            }
                        }
                    }
                }
            }
        }
    }
}
