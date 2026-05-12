package com.manik.weathersnap.navigation

sealed class Routes(val route: String) {
    data object Search : Routes("search_screen")
    data object Weather : Routes("weather_screen")
}
