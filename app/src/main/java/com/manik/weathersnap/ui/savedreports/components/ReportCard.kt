package com.manik.weathersnap.ui.savedreports.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.manik.weathersnap.data.local.ReportEntity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.shape.RoundedCornerShape
import com.manik.weathersnap.ui.theme.SkyBlue
import com.manik.weathersnap.utils.extensions.toFormattedDate
import com.manik.weathersnap.ui.theme.SkyBlue
import com.manik.weathersnap.utils.extensions.toFormattedDate

@Composable
fun ReportCard(
    report: ReportEntity,
    modifier: Modifier = Modifier
) {
    val dateString = report.timestamp.toFormattedDate()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // Image Section
            AsyncImage(
                model = report.imagePath,
                contentDescription = "Weather Report Image",
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = report.cityName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${report.temperature}°",
                        style = MaterialTheme.typography.headlineMedium,
                        color = SkyBlue
                    )
                }

                Text(
                    text = dateString,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    com.manik.weathersnap.ui.common.WeatherMetric(
                        label = "CONDITION",
                        value = report.condition,
                        labelColor = Color.White.copy(alpha = 0.3f),
                        valueColor = Color.White
                    )
                    com.manik.weathersnap.ui.common.WeatherMetric(
                        label = "HUMIDITY",
                        value = "${report.humidity}%",
                        labelColor = Color.White.copy(alpha = 0.3f),
                        valueColor = Color.White
                    )
                }

                if (report.notes.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = report.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
