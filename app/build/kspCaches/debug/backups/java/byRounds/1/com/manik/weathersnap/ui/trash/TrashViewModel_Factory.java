package com.manik.weathersnap.ui.trash;

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
public final class TrashViewModel_Factory implements Factory<TrashViewModel> {
  private final Provider<WeatherRepository> repositoryProvider;

  public TrashViewModel_Factory(Provider<WeatherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public TrashViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static TrashViewModel_Factory create(Provider<WeatherRepository> repositoryProvider) {
    return new TrashViewModel_Factory(repositoryProvider);
  }

  public static TrashViewModel newInstance(WeatherRepository repository) {
    return new TrashViewModel(repository);
  }
}
