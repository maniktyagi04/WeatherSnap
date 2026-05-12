package com.manik.weathersnap.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        // Check cache first
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

    /**
     * Fetches current weather for the selected [city] and updates the UI state.
     */
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
}
