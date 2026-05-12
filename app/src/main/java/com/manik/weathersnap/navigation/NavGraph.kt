package com.manik.weathersnap.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manik.weathersnap.ui.weather.SearchScreen
import com.manik.weathersnap.ui.weather.WeatherScreen
import com.manik.weathersnap.ui.weather.WeatherViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {
    // Shared ViewModel for search and weather screens
    val viewModel: WeatherViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Search.route
    ) {
        composable(route = Routes.Search.route) {
            SearchScreen(
                viewModel = viewModel,
                onNavigateToWeather = {
                    navController.navigate(Routes.Weather.route)
                }
            )
        }
        composable(route = Routes.Weather.route) {
            WeatherScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
