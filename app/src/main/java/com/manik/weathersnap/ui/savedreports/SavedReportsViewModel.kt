package com.manik.weathersnap.ui.savedreports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedReportsViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _snackbarEvent = Channel<String>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    val uiState: StateFlow<SavedReportsUiState> = repository.getActiveReports()
        .map { reports ->
            SavedReportsUiState(reports = reports, isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SavedReportsUiState(isLoading = true)
        )

    fun softDeleteReport(reportId: Int) {
        viewModelScope.launch {
            repository.softDeleteReport(reportId)
            _snackbarEvent.send("Report moved to trash")
        }
    }
}

data class SavedReportsUiState(
    val reports: List<ReportEntity> = emptyList(),
    val isLoading: Boolean = false
)
