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
import com.manik.weathersnap.ui.theme.MidnightBlue
import com.manik.weathersnap.ui.theme.SkyBlue

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
                title = { Text("TRASH", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
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
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().background(MidnightBlue)) {
            Crossfade(
                targetState = state.isLoading,
                label = "TrashLoadingCrossfade",
                modifier = Modifier.padding(innerPadding)
            ) { isLoading ->
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = SkyBlue)
                    }
                } else if (state.reports.isEmpty()) {
                    TrashEmptyState()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.reports, key = { it.id }) { report ->
                            ReportCard(
                                report = report,
                                isTrash = true,
                                onRestore = { viewModel.restoreReport(report.id) },
                                onDelete = { reportToDeletePermanently = report },
                                onEdit = {} // No edit in trash
                            )
                        }
                    }
                }
            }
        }
    }

    // Confirmation Dialog
    reportToDeletePermanently?.let { report ->
        AlertDialog(
            onDismissRequest = { reportToDeletePermanently = null },
            title = { Text("Delete Permanently?") },
            text = { Text("This action cannot be undone. The report from ${report.cityName} will be lost forever.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.permanentlyDeleteReport(report)
                        reportToDeletePermanently = null
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete Forever")
                }
            },
            dismissButton = {
                TextButton(onClick = { reportToDeletePermanently = null }) {
                    Text("Cancel")
                }
            },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = Color.White,
            textContentColor = Color.White.copy(alpha = 0.7f)
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
            modifier = Modifier.size(80.dp),
            tint = Color.White.copy(alpha = 0.1f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Your trash is empty",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Deleted reports will appear here for 30 days before being permanently removed.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.5f),
            textAlign = TextAlign.Center
        )
    }
}
