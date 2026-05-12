package com.manik.weathersnap.di

import android.content.Context
import androidx.room.Room
import com.manik.weathersnap.data.local.WeatherDao
import com.manik.weathersnap.data.local.WeatherDatabase
import com.manik.weathersnap.data.remote.GeocodingApiService
import com.manik.weathersnap.data.remote.WeatherApiService
import com.manik.weathersnap.data.repository.WeatherRepositoryImpl
import com.manik.weathersnap.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApiService(): WeatherApiService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGeocodingApiService(): GeocodingApiService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GeocodingApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao {
        return db.dao
    }

    @Provides
    @Singleton
    fun provideReportDao(db: WeatherDatabase): com.manik.weathersnap.data.local.ReportDao {
        return db.reportDao
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApi: WeatherApiService,
        geocodingApi: GeocodingApiService,
        reportDao: com.manik.weathersnap.data.local.ReportDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi, geocodingApi, reportDao)
    }
}
