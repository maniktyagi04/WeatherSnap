package com.manik.weathersnap.ui.savedreports.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.manik.weathersnap.data.local.ReportEntity
import com.manik.weathersnap.ui.theme.SkyBlue
import com.manik.weathersnap.utils.extensions.toFormattedDate

/**
 * Modern report card with an image-first layout and a context menu for actions.
 */
@Composable
fun ReportCard(
    report: ReportEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    isTrash: Boolean = false,
    onRestore: (() -> Unit)? = null
) {
    val dateString = if (isTrash) {
        "Deleted ${report.deletedAt?.toFormattedDate() ?: "recently"}"
    } else {
        report.timestamp.toFormattedDate()
    }
    
    var showMenu by remember { mutableStateOf(false) }

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
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, "More", tint = Color.White.copy(alpha = 0.5f))
                        }
                        
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            if (isTrash) {
                                DropdownMenuItem(
                                    text = { Text("Restore") },
                                    onClick = {
                                        showMenu = false
                                        onRestore?.invoke()
                                    },
                                    leadingIcon = { Icon(Icons.Default.Restore, null) }
                                )
                                Divider()
                                DropdownMenuItem(
                                    text = { Text("Delete Permanently") },
                                    onClick = {
                                        showMenu = false
                                        onDelete()
                                    },
                                    leadingIcon = { Icon(Icons.Default.DeleteForever, null, tint = MaterialTheme.colorScheme.error) }
                                )
                            } else {
                                DropdownMenuItem(
                                    text = { Text("Edit") },
                                    onClick = {
                                        showMenu = false
                                        onEdit()
                                    },
                                    leadingIcon = { Icon(Icons.Default.Edit, null) }
                                )
                                Divider()
                                DropdownMenuItem(
                                    text = { Text("Move to Trash") },
                                    onClick = {
                                        showMenu = false
                                        onDelete()
                                    },
                                    leadingIcon = { Icon(Icons.Default.Delete, null) }
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dateString,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                    Text(
                        text = "${report.temperature}°",
                        style = MaterialTheme.typography.titleLarge,
                        color = SkyBlue,
                        fontWeight = FontWeight.Black
                    )
                }

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
