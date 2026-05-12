import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.domain.repository.WeatherRepository
import com.manik.weathersnap.utils.ImageCompressor

/**
 * ViewModel for the report creation screen.
 * Handles image compression, state management for weather metrics, and persistence.
 */
@HiltViewModel
class CreateReportViewModel @Inject constructor(
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: android.content.Context,
    private val imageCompressor: ImageCompressor,
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateReportUiState())
    val uiState: StateFlow<CreateReportUiState> = _uiState.asStateFlow()

    fun initData(
        cityName: String,
        temp: String,
        humidity: String,
        windSpeed: String,
        pressure: String,
        condition: String
    ) {
        _uiState.update {
            it.copy(
                cityName = cityName,
                temperature = temp,
                humidity = humidity,
                windSpeed = windSpeed,
                pressure = pressure,
                condition = condition
            )
        }
    }

    fun onNotesChange(newNotes: String) {
        _uiState.update { it.copy(notes = newNotes) }
    }

    fun onImageCaptured(uri: Uri) {
        viewModelScope.launch {
            val originalSize = imageCompressor.getFileSizeFromUri(context, uri)
            
            // Compress in background
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
            } else {
                _uiState.update { 
                    it.copy(
                        imageUri = uri,
                        originalSize = originalSize,
                        compressedSize = "N/A"
                    )
                }
            }
        }
    }

    fun saveReport() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            
            val report = com.manik.weathersnap.data.local.ReportEntity(
                cityName = state.cityName,
                temperature = state.temperature,
                humidity = state.humidity,
                windSpeed = state.windSpeed,
                pressure = state.pressure,
                condition = state.condition,
                notes = state.notes,
                imagePath = state.imageUri?.toString() ?: "",
                originalImageSize = state.originalSize,
                compressedImageSize = state.compressedSize,
                timestamp = System.currentTimeMillis()
            )
            
            repository.saveReport(report)
            
            _uiState.update { it.copy(isSaving = false, isSuccess = true) }
        }
    }
}
