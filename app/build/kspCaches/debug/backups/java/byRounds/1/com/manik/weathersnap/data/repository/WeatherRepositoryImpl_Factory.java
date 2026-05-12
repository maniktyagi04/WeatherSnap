package com.manik.weathersnap.data.repository;

import com.manik.weathersnap.data.local.ReportDao;
import com.manik.weathersnap.data.remote.GeocodingApiService;
import com.manik.weathersnap.data.remote.WeatherApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class WeatherRepositoryImpl_Factory implements Factory<WeatherRepositoryImpl> {
  private final Provider<WeatherApiService> weatherApiProvider;

  private final Provider<GeocodingApiService> geocodingApiProvider;

  private final Provider<ReportDao> reportDaoProvider;

  public WeatherRepositoryImpl_Factory(Provider<WeatherApiService> weatherApiProvider,
      Provider<GeocodingApiService> geocodingApiProvider, Provider<ReportDao> reportDaoProvider) {
    this.weatherApiProvider = weatherApiProvider;
    this.geocodingApiProvider = geocodingApiProvider;
    this.reportDaoProvider = reportDaoProvider;
  }

  @Override
  public WeatherRepositoryImpl get() {
    return newInstance(weatherApiProvider.get(), geocodingApiProvider.get(), reportDaoProvider.get());
  }

  public static WeatherRepositoryImpl_Factory create(Provider<WeatherApiService> weatherApiProvider,
      Provider<GeocodingApiService> geocodingApiProvider, Provider<ReportDao> reportDaoProvider) {
    return new WeatherRepositoryImpl_Factory(weatherApiProvider, geocodingApiProvider, reportDaoProvider);
  }

  public static WeatherRepositoryImpl newInstance(WeatherApiService weatherApi,
      GeocodingApiService geocodingApi, ReportDao reportDao) {
    return new WeatherRepositoryImpl(weatherApi, geocodingApi, reportDao);
  }
}
