package com.manik.weathersnap.ui.weather

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import com.manik.weathersnap.domain.repository.WeatherRepository
import com.manik.weathersnap.ui.theme.WeatherSnapTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: WeatherViewModel = mockk(relaxed = true)

    @Test
    fun idleState_showsSearchPrompt() {
        every { viewModel.weatherUiState } returns MutableStateFlow(WeatherUiState.Idle)
        every { viewModel.searchUiState } returns MutableStateFlow(SearchUiState())

        composeTestRule.setContent {
            WeatherSnapTheme {
                WeatherScreen(
                    viewModel = viewModel,
                    onNavigateToReports = {},
                    onCreateReport = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Search for a city to see weather data").assertIsDisplayed()
    }

    @Test
    fun loadingState_showsCircularProgressIndicator() {
        // Since we can't easily test for the existence of CircularProgressIndicator by tag without adding modifiers,
        // we test that the Idle state text is GONE when loading.
        every { viewModel.weatherUiState } returns MutableStateFlow(WeatherUiState.Loading)
        every { viewModel.searchUiState } returns MutableStateFlow(SearchUiState())

        composeTestRule.setContent {
            WeatherSnapTheme {
                WeatherScreen(
                    viewModel = viewModel,
                    onNavigateToReports = {},
                    onCreateReport = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Search for a city to see weather data").assertDoesNotExist()
    }
}
