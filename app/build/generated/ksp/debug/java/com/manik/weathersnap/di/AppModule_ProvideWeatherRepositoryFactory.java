package com.manik.weathersnap.di;

import com.manik.weathersnap.data.local.ReportDao;
import com.manik.weathersnap.data.remote.GeocodingApiService;
import com.manik.weathersnap.data.remote.WeatherApiService;
import com.manik.weathersnap.domain.repository.WeatherRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideWeatherRepositoryFactory implements Factory<WeatherRepository> {
  private final Provider<WeatherApiService> weatherApiProvider;

  private final Provider<GeocodingApiService> geocodingApiProvider;

  private final Provider<ReportDao> reportDaoProvider;

  public AppModule_ProvideWeatherRepositoryFactory(Provider<WeatherApiService> weatherApiProvider,
      Provider<GeocodingApiService> geocodingApiProvider, Provider<ReportDao> reportDaoProvider) {
    this.weatherApiProvider = weatherApiProvider;
    this.geocodingApiProvider = geocodingApiProvider;
    this.reportDaoProvider = reportDaoProvider;
  }

  @Override
  public WeatherRepository get() {
    return provideWeatherRepository(weatherApiProvider.get(), geocodingApiProvider.get(), reportDaoProvider.get());
  }

  public static AppModule_ProvideWeatherRepositoryFactory create(
      Provider<WeatherApiService> weatherApiProvider,
      Provider<GeocodingApiService> geocodingApiProvider, Provider<ReportDao> reportDaoProvider) {
    return new AppModule_ProvideWeatherRepositoryFactory(weatherApiProvider, geocodingApiProvider, reportDaoProvider);
  }

  public static WeatherRepository provideWeatherRepository(WeatherApiService weatherApi,
      GeocodingApiService geocodingApi, ReportDao reportDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWeatherRepository(weatherApi, geocodingApi, reportDao));
  }
}
