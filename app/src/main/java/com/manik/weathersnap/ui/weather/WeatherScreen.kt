package com.manik.weathersnap.ui.weather

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.manik.weathersnap.ui.theme.*
import com.manik.weathersnap.ui.weather.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onNavigateToReports: () -> Unit,
    onCreateReport: () -> Unit,
    onNavigateToTrash: () -> Unit,
    onNavigateToEditReport: (Int) -> Unit
) {
    val searchState by viewModel.searchUiState.collectAsState()
    val weatherState by viewModel.weatherUiState.collectAsState()
    val recentReports by viewModel.recentReports.collectAsState()
    val scrollState = rememberScrollState()

    BackHandler(enabled = weatherState !is WeatherUiState.Idle) {
        viewModel.resetToIdle()
    }

    Scaffold(
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
                // Dashboard Content (Visible when Idle or as background)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    // Quick Actions
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickActionCard(
                            title = "All Reports",
                            icon = Icons.Default.History,
                            onClick = onNavigateToReports,
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionCard(
                            title = "Trash",
                            icon = Icons.Default.DeleteSweep,
                            onClick = onNavigateToTrash,
                            modifier = Modifier.weight(1f),
                            containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Recent Reports
                    if (recentReports.isNotEmpty()) {
                        SectionHeader("RECENT REPORTS", onActionClick = onNavigateToReports)
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(recentReports) { report ->
                                RecentReportCard(
                                    report = report,
                                    onClick = { onNavigateToEditReport(report.id) }
                                )
                            }
                        }
                    } else {
                        // Helper Text when no reports
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Your recent weather captures will appear here",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Weather Result Overlay
                Crossfade(targetState = weatherState, label = "WeatherState") { state ->
                    when (state) {
                        is WeatherUiState.Idle -> { /* Already showing dashboard */ }
                        is WeatherUiState.Loading -> LoadingView()
                        is WeatherUiState.Error -> ErrorView(state.message)
                        is WeatherUiState.Success -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(AppBackground.copy(alpha = 0.95f))
                            ) {
                                SectionHeader("SEARCH RESULT", showAction = false)
                                WeatherCard(
                                    weather = state.weather,
                                    city = state.city,
                                    onCreateReport = onCreateReport
                                )
                                Spacer(modifier = Modifier.height(40.dp))
                            }
                        }
                    }
                }
            }

            // Suggestions Dropdown
            SuggestionList(
                suggestions = searchState.suggestions,
                isVisible = searchState.query.length >= 2,
                onCitySelected = { city ->
                    viewModel.selectCity(city)
                    viewModel.onSearchQueryChange("") 
                },
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    showAction: Boolean = true,
    onActionClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
        if (showAction) {
            Text(
                text = "SEE ALL",
                style = MaterialTheme.typography.labelSmall,
                color = AccentBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onActionClick() }
            )
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = SurfaceSecondary,
    contentColor: Color = AccentBlue
) {
    Card(
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(12.dp),
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
            Icon(icon, null, tint = contentColor, modifier = Modifier.size(20.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = AccentBlue, strokeWidth = 2.dp)
    }
}

@Composable
fun ErrorView(message: String) {
    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
        Text(message, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
    }
}
