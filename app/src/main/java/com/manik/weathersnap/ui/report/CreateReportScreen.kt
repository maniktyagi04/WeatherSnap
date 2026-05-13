package com.manik.weathersnap.ui.report

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manik.weathersnap.ui.report.components.ImagePreview
import com.manik.weathersnap.ui.report.components.NotesInput
import com.manik.weathersnap.ui.theme.*

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
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("captured_image_uri")
        }
    }

    LaunchedEffect(cityName, temp, humidity, windSpeed, pressure, condition) {
        viewModel.initData(cityName, temp, humidity, windSpeed, pressure, condition)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "CREATE REPORT", 
                        style = MaterialTheme.typography.titleMedium, 
                        fontWeight = FontWeight.Bold, 
                        letterSpacing = 1.sp,
                        color = TextPrimary
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = AccentBlue)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppBackground
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Compact Weather Summary
            Surface(
                modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(),
                color = SurfacePrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = state.cityName, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Text(text = state.condition, style = MaterialTheme.typography.labelMedium, color = AccentBlue, fontWeight = FontWeight.Bold)
                    }
                    Text(text = "${state.temperature}°", style = MaterialTheme.typography.headlineLarge, color = TextPrimary, fontWeight = FontWeight.Black)
                }
            }

            // Image Preview Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(SurfacePrimary)
                    .clickable { onNavigateToCamera() },
                contentAlignment = Alignment.Center
            ) {
                if (state.imageUri != null) {
                    ImagePreview(
                        imageUri = state.imageUri,
                        originalSize = state.originalSize,
                        compressedSize = state.compressedSize
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AddAPhoto, null, modifier = Modifier.size(40.dp), tint = TextSecondary.copy(alpha = 0.3f))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tap to capture photo", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Notes Section
            NotesInput(
                notes = state.notes,
                onNotesChange = { viewModel.onNotesChange(it) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action Button
            Button(
                onClick = { viewModel.saveReport() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentBlue,
                    contentColor = AppBackground
                ),
                enabled = !state.isSaving && state.imageUri != null
            ) {
                Crossfade(targetState = state.isSaving, label = "SaveButton") { isSaving ->
                    if (isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = AppBackground, strokeWidth = 2.dp)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Save, null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("SAVE REPORT", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    if (state.isSuccess) {
        LaunchedEffect(Unit) {
            onBack()
        }
    }
}
