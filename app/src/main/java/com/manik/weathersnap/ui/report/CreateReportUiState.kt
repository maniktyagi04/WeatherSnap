package com.manik.weathersnap.ui.report

import android.net.Uri

data class CreateReportUiState(
    val cityName: String = "",
    val temperature: String = "",
    val notes: String = "",
    val imageUri: Uri? = null,
    val originalSize: String = "0 KB",
    val compressedSize: String = "0 KB",
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false
)
