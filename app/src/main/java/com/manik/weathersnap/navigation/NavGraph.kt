package com.manik.weathersnap.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manik.weathersnap.ui.weather.WeatherScreen
import com.manik.weathersnap.ui.weather.WeatherViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val viewModel: WeatherViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Weather.route // Starting directly on WeatherScreen now
    ) {
        composable(route = Routes.Weather.route) {
            WeatherScreen(
                viewModel = viewModel,
                onNavigateToReports = {
                    // Placeholder for now
                },
                onCreateReport = {
                    // Placeholder for now
                }
            )
        }
    }
}
