package com.manik.weathersnap.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manik.weathersnap.ui.weather.SearchScreen
import com.manik.weathersnap.ui.weather.WeatherScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Search.route
    ) {
        composable(route = Routes.Search.route) {
            SearchScreen(
                onNavigateToWeather = {
                    navController.navigate(Routes.Weather.route)
                }
            )
        }
        composable(route = Routes.Weather.route) {
            WeatherScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
