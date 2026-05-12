package com.manik.weathersnap.di;

import com.manik.weathersnap.data.remote.GeocodingApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AppModule_ProvideGeocodingApiServiceFactory implements Factory<GeocodingApiService> {
  @Override
  public GeocodingApiService get() {
    return provideGeocodingApiService();
  }

  public static AppModule_ProvideGeocodingApiServiceFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GeocodingApiService provideGeocodingApiService() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideGeocodingApiService());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideGeocodingApiServiceFactory INSTANCE = new AppModule_ProvideGeocodingApiServiceFactory();
  }
}
