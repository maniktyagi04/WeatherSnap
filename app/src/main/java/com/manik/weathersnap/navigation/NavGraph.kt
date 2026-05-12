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
import com.manik.weathersnap.ui.report.CreateReportScreen
import com.manik.weathersnap.ui.savedreports.SavedReportsScreen
import com.manik.weathersnap.ui.weather.WeatherScreen
import com.manik.weathersnap.ui.weather.WeatherUiState
import com.manik.weathersnap.ui.weather.WeatherViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val viewModel: WeatherViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Weather.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(route = Routes.Weather.route) {
            WeatherScreen(
                viewModel = viewModel,
                onNavigateToReports = {
                    navController.navigate(Routes.SavedReports.route)
                },
                onCreateReport = {
                    val state = viewModel.weatherUiState.value
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
                }
            )
        }

        composable(
            route = Routes.CreateReport.route,
            arguments = listOf(
                navArgument("cityName") { type = NavType.StringType },
                navArgument("temp") { type = NavType.StringType },
                navArgument("humidity") { type = NavType.StringType },
                navArgument("windSpeed") { type = NavType.StringType },
                navArgument("pressure") { type = NavType.StringType },
                navArgument("condition") { type = NavType.StringType }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            }
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            val temp = backStackEntry.arguments?.getString("temp") ?: ""
            val humidity = backStackEntry.arguments?.getString("humidity") ?: ""
            val windSpeed = backStackEntry.arguments?.getString("windSpeed") ?: ""
            val pressure = backStackEntry.arguments?.getString("pressure") ?: ""
            val condition = backStackEntry.arguments?.getString("condition") ?: ""

            CreateReportScreen(
                cityName = cityName,
                temp = temp,
                humidity = humidity,
                windSpeed = windSpeed,
                pressure = pressure,
                condition = condition,
                onBack = { navController.popBackStack() },
                onNavigateToCamera = { navController.navigate(Routes.Camera.route) },
                navController = navController
            )
        }

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
            SavedReportsScreen(onBack = { navController.popBackStack() })
        }
    }
}
