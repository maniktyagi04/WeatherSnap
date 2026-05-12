package com.manik.weathersnap.navigation

sealed class Routes(val route: String) {
    data object Weather : Routes("weather")
    
    data object CreateReport : Routes("create_report/{cityName}/{temp}/{humidity}/{windSpeed}/{pressure}/{condition}") {
        fun createRoute(
            cityName: String,
            temp: String,
            humidity: String,
            windSpeed: String,
            pressure: String,
            condition: String
        ) = "create_report/$cityName/$temp/$humidity/$windSpeed/$pressure/$condition"
    }
    
    data object Camera : Routes("camera")
    
    data object SavedReports : Routes("saved_reports")
}
