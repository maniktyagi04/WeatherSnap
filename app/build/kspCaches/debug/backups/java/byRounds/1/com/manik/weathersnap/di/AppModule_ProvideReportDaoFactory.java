package com.manik.weathersnap.di;

import com.manik.weathersnap.data.local.ReportDao;
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
public final class AppModule_ProvideReportDaoFactory implements Factory<ReportDao> {
  private final Provider<WeatherDatabase> dbProvider;

  public AppModule_ProvideReportDaoFactory(Provider<WeatherDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ReportDao get() {
    return provideReportDao(dbProvider.get());
  }

  public static AppModule_ProvideReportDaoFactory create(Provider<WeatherDatabase> dbProvider) {
    return new AppModule_ProvideReportDaoFactory(dbProvider);
  }

  public static ReportDao provideReportDao(WeatherDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideReportDao(db));
  }
}
