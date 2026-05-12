package com.manik.weathersnap.domain.repository

import com.google.common.truth.Truth.assertThat
import com.manik.weathersnap.data.remote.WeatherApi
import com.manik.weathersnap.data.repository.WeatherRepositoryImpl
import com.manik.weathersnap.data.local.ReportDao
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var repository: WeatherRepositoryImpl
    private val api: WeatherApi = mockk()
    private val reportDao: ReportDao = mockk()

    @Before
    fun setUp() {
        repository = WeatherRepositoryImpl(api, reportDao)
    }

    @Test
    fun `searchCity returns list of cities on success`() = runTest {
        // Given
        val query = "London"
        val mockResponse = mockk<com.manik.weathersnap.data.remote.dto.CitySearchDto>()
        coEvery { mockResponse.results } returns listOf(
            mockk {
                coEvery { id } returns 1
                coEvery { name } returns "London"
                coEvery { country } returns "UK"
                coEvery { admin1 } returns "England"
                coEvery { latitude } returns 51.5
                coEvery { longitude } returns -0.12
            }
        )
        coEvery { api.searchCity(query) } returns mockResponse

        // When
        val result = repository.searchCity(query)

        // Then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).hasSize(1)
        assertThat(result.getOrNull()?.first()?.name).isEqualTo("London")
    }

    @Test
    fun `searchCity returns failure on exception`() = runTest {
        // Given
        coEvery { api.searchCity(any()) } throws Exception("Network Error")

        // When
        val result = repository.searchCity("Berlin")

        // Then
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Network Error")
    }
}
