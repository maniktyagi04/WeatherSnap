package com.manik.weathersnap.ui.edit

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manik.weathersnap.ui.report.components.ImagePreview
import com.manik.weathersnap.ui.report.components.NotesInput
import com.manik.weathersnap.ui.theme.DeepIndigo
import com.manik.weathersnap.ui.theme.MidnightBlue
import com.manik.weathersnap.ui.theme.SkyBlue
import com.manik.weathersnap.ui.theme.SoftBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReportScreen(
    reportId: Int,
    onBack: () -> Unit,
    onCaptureImage: () -> Unit,
    viewModel: EditReportViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(reportId) {
        viewModel.loadReport(reportId)
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("EDIT REPORT", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SkyBlue)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MidnightBlue
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.updateReport() },
                containerColor = SkyBlue,
                contentColor = MidnightBlue,
                shape = RoundedCornerShape(20.dp)
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MidnightBlue, strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Default.Save, "Save Changes")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(MidnightBlue, DeepIndigo)))
                .padding(innerPadding)
        ) {
            Crossfade(targetState = state.isLoading, label = "EditLoading") { isLoading ->
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = SkyBlue)
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Image Preview Section
                        ImagePreview(
                            imageUri = state.imageUri,
                            originalSize = state.originalSize ?: state.report?.originalImageSize ?: "0 KB",
                            compressedSize = state.compressedSize ?: state.report?.compressedImageSize ?: "0 KB"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onCaptureImage,
                            colors = ButtonDefaults.buttonColors(containerColor = SoftBlue),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth().height(56.dp)
                        ) {
                            Icon(Icons.Default.CameraAlt, null, tint = SkyBlue)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("REPLACE PHOTO", color = SkyBlue, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Info section (Read-only)
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f))
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = state.report?.cityName ?: "",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${state.report?.condition} • ${state.report?.temperature}°C",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = SkyBlue
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        NotesInput(
                            notes = state.notes,
                            onNotesChange = { viewModel.onNotesChange(it) }
                        )

                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}
