package com.manik.weathersnap.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReportDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var dao: ReportDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.reportDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetAllReports() = runBlocking {
        val report = ReportEntity(
            cityName = "London",
            temperature = "20",
            humidity = "60",
            windSpeed = "10",
            pressure = "1012",
            condition = "Cloudy",
            notes = "Test note",
            imagePath = "path/to/image",
            originalImageSize = "1 MB",
            compressedImageSize = "200 KB",
            timestamp = System.currentTimeMillis()
        )

        dao.insertReport(report)
        val allReports = dao.getAllReports().first()

        assertThat(allReports).hasSize(1)
        assertThat(allReports[0].cityName).isEqualTo("London")
        assertThat(allReports[0].notes).isEqualTo("Test note")
    }
}
