package com.manik.weathersnap.ui.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.model.Weather
import com.manik.weathersnap.ui.theme.AccentBlue
import com.manik.weathersnap.ui.theme.TextPrimary
import com.manik.weathersnap.ui.theme.TextSecondary

@Composable
fun WeatherCard(
    weather: Weather,
    city: City,
    onCreateReport: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // City Info
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
                Text(
                    text = "${city.admin1 ?: ""}, ${city.country ?: ""}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Temperature & Condition
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${weather.temperature}°",
                    style = MaterialTheme.typography.displayMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = weather.weatherCondition.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = AccentBlue,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Metrics Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.03f), RoundedCornerShape(16.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricItem(Icons.Default.WaterDrop, "${weather.humidity}%")
                MetricDivider()
                MetricItem(Icons.Default.Air, "${weather.windSpeed} km/h")
                MetricDivider()
                MetricItem(Icons.Default.Compress, "${weather.pressure.toInt()} hPa")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Integrated Action Button
            Button(
                onClick = onCreateReport,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentBlue,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(Icons.Default.Add, null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("CREATE WEATHER REPORT", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
private fun MetricItem(icon: ImageVector, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, modifier = Modifier.size(18.dp), tint = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.labelMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun RowScope.MetricDivider() {
    Box(
        modifier = Modifier
            .height(24.dp)
            .width(1.dp)
            .background(Color.White.copy(alpha = 0.1f))
            .align(Alignment.CenterVertically)
    )
}
