package com.manik.weathersnap.ui.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset
import com.manik.weathersnap.domain.model.City
import com.manik.weathersnap.domain.model.Weather
import com.manik.weathersnap.ui.theme.SkyBlue

@Composable
fun WeatherCard(
    weather: Weather,
    city: City,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Gradient Glow behind the card
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(12.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(SkyBlue.copy(alpha = 0.2f), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(200f, 100f)
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp, 
                Brush.linearGradient(listOf(Color.White.copy(alpha = 0.2f), Color.Transparent))
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header: City and Country
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = city.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                    Text(
                        text = "${city.admin1 ?: ""}, ${city.country ?: ""}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Main Temperature
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${weather.temperature}",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White
                    )
                    Text(
                        text = "°",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Light,
                            color = SkyBlue
                        )
                    )
                }

                Text(
                    text = weather.weatherCondition.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp,
                    color = SkyBlue
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Metrics Grid
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White.copy(alpha = 0.05f),
                            RoundedCornerShape(24.dp)
                        )
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    com.manik.weathersnap.ui.common.WeatherMetric(
                        label = "HUMIDITY",
                        value = "${weather.humidity}%",
                        labelColor = Color.White.copy(alpha = 0.4f),
                        valueColor = Color.White,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp),
                        color = Color.White.copy(alpha = 0.1f)
                    )
                    com.manik.weathersnap.ui.common.WeatherMetric(
                        label = "WIND",
                        value = "${weather.windSpeed} km/h",
                        labelColor = Color.White.copy(alpha = 0.4f),
                        valueColor = Color.White,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp),
                        color = Color.White.copy(alpha = 0.1f)
                    )
                    com.manik.weathersnap.ui.common.WeatherMetric(
                        label = "PRESSURE",
                        value = "${weather.pressure.toInt()} hPa",
                        labelColor = Color.White.copy(alpha = 0.4f),
                        valueColor = Color.White,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                }
            }
        }
    }
}
