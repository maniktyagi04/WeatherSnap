package com.manik.weathersnap.ui.savedreports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SavedReportsViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    val uiState: StateFlow<SavedReportsUiState> = repository.getAllReports()
        .map { reports ->
            SavedReportsUiState(reports = reports)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SavedReportsUiState(isLoading = true)
        )
}

data class SavedReportsUiState(
    val reports: List<ReportEntity> = emptyList(),
    val isLoading: Boolean = false
)
