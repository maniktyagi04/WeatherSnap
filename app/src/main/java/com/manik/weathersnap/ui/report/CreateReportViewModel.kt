package com.manik.weathersnap.ui.report

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateReportViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CreateReportUiState())
    val uiState: StateFlow<CreateReportUiState> = _uiState.asStateFlow()

    fun initData(cityName: String, temp: String) {
        _uiState.update { it.copy(cityName = cityName, temperature = temp) }
    }

    fun onNotesChange(newNotes: String) {
        _uiState.update { it.copy(notes = newNotes) }
    }

    fun onImageCaptured(uri: Uri) {
        // Placeholder size calculation
        _uiState.update { 
            it.copy(
                imageUri = uri,
                originalSize = "2.4 MB",
                compressedSize = "450 KB"
            )
        }
    }

    fun saveReport() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            delay(1500) // Simulate saving
            _uiState.update { it.copy(isSaving = false, isSuccess = true) }
        }
    }
}
