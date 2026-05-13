package com.manik.weathersnap.ui.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    val uiState: StateFlow<TrashUiState> = repository.getTrashReports()
        .map { reports ->
            TrashUiState(reports = reports)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TrashUiState(isLoading = true)
        )

    fun restoreReport(reportId: Int) {
        viewModelScope.launch {
            repository.restoreReport(reportId)
        }
    }

    fun permanentlyDeleteReport(report: ReportEntity) {
        viewModelScope.launch {
            repository.permanentlyDeleteReport(report)
        }
    }
}

data class TrashUiState(
    val reports: List<ReportEntity> = emptyList(),
    val isLoading: Boolean = false
)
