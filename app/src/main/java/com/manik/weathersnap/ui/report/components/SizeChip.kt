package com.manik.weathersnap.ui.report.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SizeChip(
    label: String,
    size: String,
    containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSecondaryContainer
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = "$label: $size",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            color = contentColor
        )
    }
}
