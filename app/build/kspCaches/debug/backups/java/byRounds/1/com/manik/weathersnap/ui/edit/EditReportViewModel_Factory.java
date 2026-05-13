package com.manik.weathersnap.ui.edit;

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
public final class EditReportViewModel_Factory implements Factory<EditReportViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<WeatherRepository> repositoryProvider;

  private final Provider<ImageCompressor> imageCompressorProvider;

  public EditReportViewModel_Factory(Provider<Context> contextProvider,
      Provider<WeatherRepository> repositoryProvider,
      Provider<ImageCompressor> imageCompressorProvider) {
    this.contextProvider = contextProvider;
    this.repositoryProvider = repositoryProvider;
    this.imageCompressorProvider = imageCompressorProvider;
  }

  @Override
  public EditReportViewModel get() {
    return newInstance(contextProvider.get(), repositoryProvider.get(), imageCompressorProvider.get());
  }

  public static EditReportViewModel_Factory create(Provider<Context> contextProvider,
      Provider<WeatherRepository> repositoryProvider,
      Provider<ImageCompressor> imageCompressorProvider) {
    return new EditReportViewModel_Factory(contextProvider, repositoryProvider, imageCompressorProvider);
  }

  public static EditReportViewModel newInstance(Context context, WeatherRepository repository,
      ImageCompressor imageCompressor) {
    return new EditReportViewModel(context, repository, imageCompressor);
  }
}
