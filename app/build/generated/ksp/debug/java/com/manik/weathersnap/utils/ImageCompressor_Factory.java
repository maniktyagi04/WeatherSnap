package com.manik.weathersnap.utils;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ImageCompressor_Factory implements Factory<ImageCompressor> {
  @Override
  public ImageCompressor get() {
    return newInstance();
  }

  public static ImageCompressor_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ImageCompressor newInstance() {
    return new ImageCompressor();
  }

  private static final class InstanceHolder {
    private static final ImageCompressor_Factory INSTANCE = new ImageCompressor_Factory();
  }
}
