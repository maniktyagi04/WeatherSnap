package com.manik.weathersnap.ui.report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.manik.weathersnap.ui.report.components.ImagePreview
import com.manik.weathersnap.ui.report.components.NotesInput
import com.manik.weathersnap.ui.report.components.SizeChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReportScreen(
    cityName: String,
    temp: String,
    humidity: String,
    windSpeed: String,
    pressure: String,
    condition: String,
    onBack: () -> Unit,
    onNavigateToCamera: () -> Unit,
    navController: androidx.navigation.NavHostController,
    viewModel: CreateReportViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val capturedImageUri = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("captured_image_uri")
        ?.observeAsState()

    LaunchedEffect(capturedImageUri?.value) {
        capturedImageUri?.value?.let { uriString ->
            viewModel.onImageCaptured(android.net.Uri.parse(uriString))
            // Clear the value so it doesn't trigger again on configuration change
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("captured_image_uri")
        }
    }

    LaunchedEffect(cityName, temp, humidity, windSpeed, pressure, condition) {
        viewModel.initData(cityName, temp, humidity, windSpeed, pressure, condition)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Report") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 3.dp,
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = { viewModel.saveReport() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.large,
                    enabled = !state.isSaving
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(Icons.Default.Save, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save Report")
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Weather Summary Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = state.cityName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Current Conditions",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                    Text(
                        text = "${state.temperature}°C",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // Image Section
            ImagePreview(imageUri = state.imageUri)
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (state.imageUri != null) {
                    SizeChip(label = "Original", size = state.originalSize)
                    SizeChip(label = "Compressed", size = state.compressedSize, containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                }
            }

            OutlinedButton(
                onClick = onNavigateToCamera,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Filled.AddAPhoto, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (state.imageUri == null) "Capture Photo" else "Retake Photo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notes Section
            NotesInput(
                notes = state.notes,
                onNotesChange = { viewModel.onNotesChange(it) }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (state.isSuccess) {
        LaunchedEffect(Unit) {
            onBack()
        }
    }
}
