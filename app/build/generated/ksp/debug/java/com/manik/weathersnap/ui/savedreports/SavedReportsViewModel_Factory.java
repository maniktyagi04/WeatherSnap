package com.manik.weathersnap.ui.savedreports;

import com.manik.weathersnap.domain.repository.WeatherRepository;
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
public final class SavedReportsViewModel_Factory implements Factory<SavedReportsViewModel> {
  private final Provider<WeatherRepository> repositoryProvider;

  public SavedReportsViewModel_Factory(Provider<WeatherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SavedReportsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static SavedReportsViewModel_Factory create(
      Provider<WeatherRepository> repositoryProvider) {
    return new SavedReportsViewModel_Factory(repositoryProvider);
  }

  public static SavedReportsViewModel newInstance(WeatherRepository repository) {
    return new SavedReportsViewModel(repository);
  }
}
