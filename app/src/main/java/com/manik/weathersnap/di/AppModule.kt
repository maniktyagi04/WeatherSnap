package com.manik.weathersnap.di

import android.content.Context
import androidx.room.Room
import com.manik.weathersnap.BuildConfig
import com.manik.weathersnap.data.local.ReportDao
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
        val clientBuilder = OkHttpClient.Builder()
        
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            clientBuilder.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
            .create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGeocodingApiService(): GeocodingApiService {
        val clientBuilder = OkHttpClient.Builder()
        
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            clientBuilder.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
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
    fun provideReportDao(db: WeatherDatabase): ReportDao {
        return db.reportDao
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApi: WeatherApiService,
        geocodingApi: GeocodingApiService,
        reportDao: ReportDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi, geocodingApi, reportDao)
    }
}
