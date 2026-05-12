package com.manik.weathersnap.ui.report;

import android.content.Context;
import com.manik.weathersnap.domain.repository.WeatherRepository;
import com.manik.weathersnap.utils.ImageCompressor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class CreateReportViewModel_Factory implements Factory<CreateReportViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ImageCompressor> imageCompressorProvider;

  private final Provider<WeatherRepository> repositoryProvider;

  public CreateReportViewModel_Factory(Provider<Context> contextProvider,
      Provider<ImageCompressor> imageCompressorProvider,
      Provider<WeatherRepository> repositoryProvider) {
    this.contextProvider = contextProvider;
    this.imageCompressorProvider = imageCompressorProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CreateReportViewModel get() {
    return newInstance(contextProvider.get(), imageCompressorProvider.get(), repositoryProvider.get());
  }

  public static CreateReportViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ImageCompressor> imageCompressorProvider,
      Provider<WeatherRepository> repositoryProvider) {
    return new CreateReportViewModel_Factory(contextProvider, imageCompressorProvider, repositoryProvider);
  }

  public static CreateReportViewModel newInstance(Context context, ImageCompressor imageCompressor,
      WeatherRepository repository) {
    return new CreateReportViewModel(context, imageCompressor, repository);
  }
}
