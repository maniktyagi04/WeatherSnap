package com.manik.weathersnap.navigation

sealed class Routes(val route: String) {
    data object Weather : Routes("weather")
    
    data object CreateReport : Routes("create_report/{cityName}/{temp}") {
        fun createRoute(cityName: String, temp: String) = "create_report/$cityName/$temp"
    }
    
    data object Camera : Routes("camera")
    
    data object SavedReports : Routes("saved_reports")
}
