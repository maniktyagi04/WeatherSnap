package com.manik.weathersnap.ui.trash

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
class TrashViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _snackbarEvent = Channel<String>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    val uiState: StateFlow<TrashUiState> = repository.getTrashReports()
        .map { reports ->
            TrashUiState(reports = reports, isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TrashUiState(isLoading = true)
        )

    fun restoreReport(reportId: Int) {
        viewModelScope.launch {
            repository.restoreReport(reportId)
            _snackbarEvent.send("Report restored")
        }
    }

    fun permanentlyDeleteReport(report: ReportEntity) {
        viewModelScope.launch {
            repository.permanentlyDeleteReport(report)
            _snackbarEvent.send("Report permanently deleted")
        }
    }
}

data class TrashUiState(
    val reports: List<ReportEntity> = emptyList(),
    val isLoading: Boolean = false
)
