package com.manik.weathersnap.ui.trash

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.ui.savedreports.components.ReportCard
import com.manik.weathersnap.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    onBack: () -> Unit,
    viewModel: TrashViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var reportToDeletePermanently by remember { mutableStateOf<ReportEntity?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "TRASH", 
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
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
            Crossfade(
                targetState = state.isLoading,
                label = "TrashLoading",
                modifier = Modifier.padding(innerPadding)
            ) { isLoading ->
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AccentBlue, strokeWidth = 2.dp)
                    }
                } else if (state.reports.isEmpty()) {
                    TrashEmptyState()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.reports, key = { it.id }) { report ->
                            ReportCard(
                                report = report,
                                isTrash = true,
                                onRestore = { viewModel.restoreReport(report.id) },
                                onDelete = { reportToDeletePermanently = report },
                                onEdit = {}
                            )
                        }
                    }
                }
            }
        }
    }

    if (reportToDeletePermanently != null) {
        AlertDialog(
            onDismissRequest = { reportToDeletePermanently = null },
            title = { Text("Delete Permanently?") },
            text = { Text("This action cannot be undone. The report will be lost forever.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        reportToDeletePermanently?.let { viewModel.permanentlyDeleteReport(it) }
                        reportToDeletePermanently = null
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed)
                ) {
                    Text("Delete Forever", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { reportToDeletePermanently = null }) {
                    Text("Cancel")
                }
            },
            containerColor = SurfacePrimary,
            titleContentColor = TextPrimary,
            textContentColor = TextSecondary
        )
    }
}

@Composable
fun TrashEmptyState() {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.DeleteSweep,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = SurfaceSecondary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Your trash is empty",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Deleted reports are kept for 30 days.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
    }
}
