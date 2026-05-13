package com.manik.weathersnap.ui.savedreports

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.animation.Crossfade
import com.manik.weathersnap.ui.savedreports.components.EmptyState
import com.manik.weathersnap.ui.savedreports.components.ReportCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedReportsScreen(
    onBack: () -> Unit,
    onEditReport: (Int) -> Unit,
    viewModel: SavedReportsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("SAVED REPORTS", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = com.manik.weathersnap.ui.theme.SkyBlue)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = com.manik.weathersnap.ui.theme.MidnightBlue
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().background(com.manik.weathersnap.ui.theme.MidnightBlue)) {
            Crossfade(
                targetState = state.isLoading, 
                label = "LoadingCrossfade",
                modifier = Modifier.padding(innerPadding)
            ) { isLoading ->
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(strokeWidth = 3.dp, color = com.manik.weathersnap.ui.theme.SkyBlue)
                    }
                } else if (state.reports.isEmpty()) {
                    EmptyState()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            end = 20.dp,
                            top = 16.dp,
                            bottom = 80.dp 
                        ),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(
                            items = state.reports,
                            key = { it.id }
                        ) { report ->
                            ReportCard(
                                report = report,
                                onEdit = { onEditReport(report.id) },
                                onDelete = { viewModel.softDeleteReport(report.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
