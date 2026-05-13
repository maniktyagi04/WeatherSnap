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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.animation.Crossfade
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.shape.RoundedCornerShape
import com.manik.weathersnap.ui.report.components.ImagePreview
import com.manik.weathersnap.ui.report.components.NotesInput
import com.manik.weathersnap.ui.report.components.SizeChip
import com.manik.weathersnap.ui.theme.SkyBlue
import com.manik.weathersnap.ui.theme.MidnightBlue
import com.manik.weathersnap.ui.theme.DeepIndigo
import com.manik.weathersnap.ui.theme.SoftBlue

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
                title = { Text("CREATE REPORT", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SkyBlue)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MidnightBlue
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MidnightBlue)
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Weather Overview Chip
            Surface(
                modifier = Modifier.padding(vertical = 16.dp),
                color = DeepIndigo,
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = state.cityName, style = MaterialTheme.typography.headlineSmall, color = Color.White)
                        Text(text = state.condition, style = MaterialTheme.typography.bodyMedium, color = SkyBlue)
                    }
                    Text(text = "${state.temperature}°", style = MaterialTheme.typography.displayMedium, color = Color.White)
                }
            }

            // Image Preview Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(32.dp))
                    .background(DeepIndigo)
                    .clickable { onNavigateToCamera() },
                contentAlignment = Alignment.Center
            ) {
                if (state.imageUri != null) {
                    ImagePreview(
                        imageUri = state.imageUri,
                        originalSize = state.originalSize,
                        compressedSize = state.compressedSize
                    )
                    
                    // Optimization Badge
                    Surface(
                        modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                        color = MidnightBlue.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "OPTIMIZED",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = SkyBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AddAPhoto, null, modifier = Modifier.size(48.dp), tint = Color.White.copy(alpha = 0.2f))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Tap to capture weather", color = Color.White.copy(alpha = 0.4f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Notes Input
            NotesInput(
                notes = state.notes,
                onNotesChange = { viewModel.onNotesChange(it) }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { viewModel.saveReport() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SkyBlue,
                    disabledContainerColor = SoftBlue
                ),
                enabled = !state.isSaving && state.imageUri != null
            ) {
                Crossfade(targetState = state.isSaving, label = "SaveButtonCrossfade") { isSaving ->
                    if (isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MidnightBlue)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Save, null, tint = MidnightBlue)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("SAVE REPORT", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = MidnightBlue)
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

