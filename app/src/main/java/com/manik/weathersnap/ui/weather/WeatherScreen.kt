package com.manik.weathersnap.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manik.weathersnap.ui.savedreports.components.ReportCard
import com.manik.weathersnap.ui.theme.*
import com.manik.weathersnap.ui.weather.components.*
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onNavigateToReports: () -> Unit,
    onNavigateToTrash: () -> Unit,
    onNavigateToEditReport: (Int) -> Unit,
    onNavigateToWeatherDetails: () -> Unit
) {
    val searchState by viewModel.searchUiState.collectAsState()
    val recentReports by viewModel.recentReports.collectAsState()
    val trashCount by viewModel.trashCount.collectAsState()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column(
                modifier = Modifier
                    .background(AppBackground)
                    .padding(top = 12.dp, bottom = 8.dp)
            ) {
                WeatherSearchBar(
                    query = searchState.query,
                    onQueryChange = { viewModel.onSearchQueryChange(it) },
                    onClearQuery = { viewModel.onSearchQueryChange("") }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // Quick Actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = "Saved Reports",
                        icon = Icons.Default.History,
                        onClick = onNavigateToReports,
                        modifier = Modifier.weight(1f)
                    )
                    
                    val trashTitle = if (trashCount > 0) "Trash ($trashCount)" else "Trash Bin"
                    QuickActionCard(
                        title = trashTitle,
                        icon = Icons.Default.DeleteSweep,
                        onClick = onNavigateToTrash,
                        modifier = Modifier.weight(1f),
                        containerColor = if (trashCount > 0) MaterialTheme.colorScheme.error.copy(alpha = 0.1f) else SurfacePrimary,
                        contentColor = if (trashCount > 0) MaterialTheme.colorScheme.error else AccentBlue
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Recent Reports Section (Vertical Latest 3)
                if (recentReports.isNotEmpty()) {
                    SectionHeader("LATEST REPORTS")
                    
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        recentReports.forEach { report ->
                            ReportCard(
                                report = report,
                                onEdit = { onNavigateToEditReport(report.id) },
                                onDelete = { viewModel.softDeleteReport(report.id) },
                                modifier = Modifier.height(140.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        OutlinedButton(
                            onClick = onNavigateToReports,
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentBlue),
                            border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
                        ) {
                            Text("VIEW ALL REPORTS", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Start capturing weather to see reports here",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }

            // Suggestions Dropdown
            SuggestionList(
                suggestions = searchState.suggestions,
                isVisible = searchState.query.length > 2,
                onCitySelected = { city ->
                    viewModel.selectCity(city)
                    viewModel.onSearchQueryChange("") 
                    onNavigateToWeatherDetails()
                },
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = SurfacePrimary,
    contentColor: Color = AccentBlue
) {
    Card(
        modifier = modifier.height(64.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(icon, null, tint = contentColor, modifier = Modifier.size(24.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}
