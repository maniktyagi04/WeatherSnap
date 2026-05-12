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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.animation.Crossfade
import com.manik.weathersnap.ui.savedreports.components.EmptyState
import com.manik.weathersnap.ui.savedreports.components.ReportCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedReportsScreen(
    onBack: () -> Unit,
    viewModel: SavedReportsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Saved Reports", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Crossfade(
            targetState = state.isLoading, 
            label = "LoadingCrossfade",
            modifier = Modifier.padding(innerPadding)
        ) { isLoading ->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(strokeWidth = 3.dp)
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
                        bottom = 32.dp // Extra bottom padding for visibility
                    ),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(
                        items = state.reports,
                        key = { it.id } // Use ID for better scrolling performance
                    ) { report ->
                        ReportCard(report = report)
                    }
                }
            }
        }
    }
}
