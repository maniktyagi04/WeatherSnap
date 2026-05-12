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
import androidx.compose.animation.Crossfade
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.shape.RoundedCornerShape
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
            CenterAlignedTopAppBar(
                title = { Text("Create Report", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 16.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Button(
                    onClick = { viewModel.saveReport() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(56.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    enabled = !state.isSaving,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Crossfade(targetState = state.isSaving, label = "SaveButtonCrossfade") { isSaving ->
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 3.dp
                            )
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Save, null)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Publish Report", style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Weather Summary Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = state.cityName,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = state.condition,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                        )
                    }
                    Text(
                        text = "${state.temperature}°",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Image Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                ImagePreview(imageUri = state.imageUri)
            }
            
            AnimatedVisibility(visible = state.imageUri != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SizeChip(label = "Raw", size = state.originalSize)
                    Spacer(modifier = Modifier.width(8.dp))
                    SizeChip(
                        label = "Optimized", 
                        size = state.compressedSize, 
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            FilledTonalButton(
                onClick = onNavigateToCamera,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(Icons.Filled.AddAPhoto, null)
                Spacer(modifier = Modifier.width(12.dp))
                Text(if (state.imageUri == null) "Capture Moment" else "Replace Photo")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Notes Section
            NotesInput(
                notes = state.notes,
                onNotesChange = { viewModel.onNotesChange(it) }
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    if (state.isSuccess) {
        LaunchedEffect(Unit) {
            onBack()
        }
    }
}
