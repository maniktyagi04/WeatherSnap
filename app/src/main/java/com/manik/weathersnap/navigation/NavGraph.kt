package com.manik.weathersnap.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.manik.weathersnap.ui.camera.CameraScreen
import com.manik.weathersnap.ui.edit.EditReportScreen
import com.manik.weathersnap.ui.report.CreateReportScreen
import com.manik.weathersnap.ui.savedreports.SavedReportsScreen
import com.manik.weathersnap.ui.trash.TrashScreen
import com.manik.weathersnap.ui.weather.WeatherScreen
import com.manik.weathersnap.ui.weather.WeatherUiState
import com.manik.weathersnap.ui.weather.WeatherViewModel

/**
 * Sets up the central navigation graph for the application.
 * Manages screen transitions and argument passing between modules.
 */
@Composable
fun SetupNavGraph(navController: NavHostController) {
    val sharedWeatherViewModel: WeatherViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Weather.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        // Main Weather Display Screen
        composable(route = Routes.Weather.route) {
            WeatherScreen(
                viewModel = sharedWeatherViewModel,
                onNavigateToReports = {
                    navController.navigate(Routes.SavedReports.route)
                },
                onCreateReport = {
                    val state = sharedWeatherViewModel.weatherUiState.value
                    if (state is WeatherUiState.Success) {
                        navController.navigate(
                            Routes.CreateReport.createRoute(
                                cityName = state.city.name,
                                temp = state.weather.temperature.toString(),
                                humidity = state.weather.humidity.toString(),
                                windSpeed = state.weather.windSpeed.toString(),
                                pressure = state.weather.pressure.toString(),
                                condition = state.weather.weatherCondition
                            )
                        )
                    }
                },
                onNavigateToTrash = {
                    navController.navigate(Routes.Trash.route)
                }
            )
        }

        // Report Creation Screen with parameter injection
        composable(
            route = Routes.CreateReport.route,
            arguments = listOf(
                navArgument("cityName") { type = NavType.StringType },
                navArgument("temp") { type = NavType.StringType },
                navArgument("humidity") { type = NavType.StringType },
                navArgument("windSpeed") { type = NavType.StringType },
                navArgument("pressure") { type = NavType.StringType },
                navArgument("condition") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments
            CreateReportScreen(
                cityName = args?.getString("cityName") ?: "",
                temp = args?.getString("temp") ?: "",
                humidity = args?.getString("humidity") ?: "",
                windSpeed = args?.getString("windSpeed") ?: "",
                pressure = args?.getString("pressure") ?: "",
                condition = args?.getString("condition") ?: "",
                onBack = { navController.popBackStack() },
                onNavigateToCamera = { navController.navigate(Routes.Camera.route) },
                navController = navController
            )
        }

        // Report Editing Screen
        composable(
            route = Routes.EditReport.route,
            arguments = listOf(
                navArgument("reportId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getInt("reportId") ?: 0
            EditReportScreen(
                reportId = reportId,
                onBack = { navController.popBackStack() },
                onCaptureImage = { navController.navigate(Routes.Camera.route) }
            )
        }

        // Camera Capture Screen
        composable(route = Routes.Camera.route) {
            CameraScreen(
                onImageCaptured = { uri ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("captured_image_uri", uri.toString())
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = Routes.SavedReports.route) {
            SavedReportsScreen(
                onBack = {
                    sharedWeatherViewModel.resetToIdle()
                    navController.popBackStack()
                },
                onEditReport = { id ->
                    navController.navigate(Routes.EditReport.createRoute(id))
                }
            )
        }

        // Trash / Recently Deleted Screen
        composable(route = Routes.Trash.route) {
            TrashScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
