package com.manik.weathersnap.di;

import android.content.Context;
import com.manik.weathersnap.data.local.WeatherDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideWeatherDatabaseFactory implements Factory<WeatherDatabase> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideWeatherDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WeatherDatabase get() {
    return provideWeatherDatabase(contextProvider.get());
  }

  public static AppModule_ProvideWeatherDatabaseFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideWeatherDatabaseFactory(contextProvider);
  }

  public static WeatherDatabase provideWeatherDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWeatherDatabase(context));
  }
}
