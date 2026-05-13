package com.manik.weathersnap.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    private val _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    
    private val _snackbarEvent = Channel<String>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    val recentReports: StateFlow<List<ReportEntity>> = repository.getActiveReports()
        .map { it.take(3) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val trashCount: StateFlow<Int> = repository.getTrashReports()
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // In-memory cache for suggestions
    private val suggestionCache = mutableMapOf<String, List<City>>()

    init {
        _searchQuery
            .debounce(500L)
            .distinctUntilChanged()
            .filter { it.length >= 2 }
            .onEach { query ->
                searchCity(query)
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        _searchUiState.value = _searchUiState.value.copy(query = newQuery)
    }

    private fun searchCity(query: String) {
        if (suggestionCache.containsKey(query)) {
            _searchUiState.value = _searchUiState.value.copy(
                suggestions = suggestionCache[query] ?: emptyList(),
                isLoading = false
            )
            return
        }

        viewModelScope.launch {
            _searchUiState.value = _searchUiState.value.copy(isLoading = true)
            repository.searchCity(query)
                .onSuccess { cities ->
                    suggestionCache[query] = cities
                    _searchUiState.value = _searchUiState.value.copy(
                        suggestions = cities,
                        isLoading = false
                    )
                }
                .onFailure {
                    _searchUiState.value = _searchUiState.value.copy(isLoading = false)
                }
        }
    }

    fun selectCity(city: City) {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading
            repository.getWeather(city.latitude, city.longitude)
                .onSuccess { weather ->
                    _weatherUiState.value = WeatherUiState.Success(weather, city)
                }
                .onFailure {
                    _weatherUiState.value = WeatherUiState.Error(it.message ?: "Unknown Error")
                }
        }
    }

    fun softDeleteReport(reportId: Int) {
        viewModelScope.launch {
            repository.softDeleteReport(reportId)
            _snackbarEvent.send("Report moved to trash")
        }
    }

    fun resetToIdle() {
        _weatherUiState.value = WeatherUiState.Idle
        _searchQuery.value = ""
        _searchUiState.value = SearchUiState()
    }
}
