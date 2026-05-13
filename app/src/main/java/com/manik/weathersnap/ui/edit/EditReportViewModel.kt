package com.manik.weathersnap.ui.edit

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.domain.repository.WeatherRepository
import com.manik.weathersnap.utils.ImageCompressor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditReportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: WeatherRepository,
    private val imageCompressor: ImageCompressor
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditReportUiState())
    val uiState: StateFlow<EditReportUiState> = _uiState.asStateFlow()

    private var originalReport: ReportEntity? = null

    fun loadReport(reportId: Int) {
        viewModelScope.launch {
            repository.getReportById(reportId)?.let { report ->
                originalReport = report
                _uiState.update {
                    it.copy(
                        report = report,
                        notes = report.notes,
                        imageUri = Uri.parse(report.imagePath),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onNotesChange(newNotes: String) {
        _uiState.update { it.copy(notes = newNotes) }
    }

    fun onImageCaptured(uri: Uri) {
        viewModelScope.launch {
            val originalSize = imageCompressor.getFileSizeFromUri(context, uri)
            val compressedFile = imageCompressor.compressImage(context, uri)
            
            if (compressedFile != null) {
                val compressedSize = imageCompressor.getFileSize(compressedFile)
                val compressedUri = Uri.fromFile(compressedFile)
                _uiState.update { 
                    it.copy(
                        imageUri = compressedUri,
                        originalSize = originalSize,
                        compressedSize = compressedSize
                    )
                }
            }
        }
    }

    fun updateReport() {
        val state = _uiState.value
        val report = originalReport ?: return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            
            val updatedReport = report.copy(
                notes = state.notes,
                imagePath = state.imageUri?.toString() ?: report.imagePath,
                originalImageSize = state.originalSize ?: report.originalImageSize,
                compressedImageSize = state.compressedSize ?: report.compressedImageSize,
                timestamp = System.currentTimeMillis() // Update timestamp to show it's edited
            )
            
            repository.updateReport(updatedReport)
            _uiState.update { it.copy(isSaving = false, isSuccess = true) }
        }
    }
}

data class EditReportUiState(
    val report: ReportEntity? = null,
    val notes: String = "",
    val imageUri: Uri? = null,
    val originalSize: String? = null,
    val compressedSize: String? = null,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false
)
