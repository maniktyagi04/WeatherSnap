package com.manik.weathersnap.di;

import com.manik.weathersnap.data.local.WeatherDao;
import com.manik.weathersnap.data.local.WeatherDatabase;
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
public final class AppModule_ProvideWeatherDaoFactory implements Factory<WeatherDao> {
  private final Provider<WeatherDatabase> dbProvider;

  public AppModule_ProvideWeatherDaoFactory(Provider<WeatherDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public WeatherDao get() {
    return provideWeatherDao(dbProvider.get());
  }

  public static AppModule_ProvideWeatherDaoFactory create(Provider<WeatherDatabase> dbProvider) {
    return new AppModule_ProvideWeatherDaoFactory(dbProvider);
  }

  public static WeatherDao provideWeatherDao(WeatherDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWeatherDao(db));
  }
}
