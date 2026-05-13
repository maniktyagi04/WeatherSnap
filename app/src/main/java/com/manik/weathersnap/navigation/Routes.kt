package com.manik.weathersnap.navigation

import android.net.Uri

sealed class Routes(val route: String) {
    data object Weather : Routes("weather")
    data object WeatherDetails : Routes("weather_details")
    
    data object CreateReport : Routes("create_report/{cityName}/{temp}/{humidity}/{windSpeed}/{pressure}/{condition}") {
        fun createRoute(
            cityName: String,
            temp: String,
            humidity: String,
            windSpeed: String,
            pressure: String,
            condition: String
        ) = "create_report/${Uri.encode(cityName)}/${Uri.encode(temp)}/${Uri.encode(humidity)}/${Uri.encode(windSpeed)}/${Uri.encode(pressure)}/${Uri.encode(condition)}"
    }
    
    data object EditReport : Routes("edit_report/{reportId}") {
        fun createRoute(reportId: Int) = "edit_report/$reportId"
    }
    
    data object Camera : Routes("camera")
    data object SavedReports : Routes("saved_reports")
    data object Trash : Routes("trash")
}
