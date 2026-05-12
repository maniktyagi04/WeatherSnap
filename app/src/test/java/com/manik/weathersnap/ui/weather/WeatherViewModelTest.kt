package com.manik.weathersnap.ui.weather

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.manik.weathersnap.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private val repository: WeatherRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSearchQueryChange updates query and fetches suggestions`() = runTest {
        // Given
        val query = "Lon"
        coEvery { repository.searchCity(query) } returns Result.success(emptyList())

        // When
        viewModel.onSearchQueryChange(query)
        advanceUntilIdle()

        // Then
        viewModel.searchUiState.test {
            val state = awaitItem()
            assertThat(state.query).isEqualTo(query)
        }
    }

    @Test
    fun `selectCity triggers weather fetch and updates state to Success`() = runTest {
        // Given
        val city = mockk<com.manik.weathersnap.domain.model.City> {
            coEvery { name } returns "London"
            coEvery { latitude } returns 51.5
            coEvery { longitude } returns -0.12
            coEvery { country } returns "UK"
            coEvery { admin1 } returns "England"
        }
        val weather = mockk<com.manik.weathersnap.domain.model.Weather>()
        coEvery { repository.getWeather(any(), any()) } returns Result.success(weather)

        // When
        viewModel.selectCity(city)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.weatherUiState.value).isInstanceOf(WeatherUiState.Success::class.java)
        val successState = viewModel.weatherUiState.value as WeatherUiState.Success
        assertThat(successState.city.name).isEqualTo("London")
    }
}
