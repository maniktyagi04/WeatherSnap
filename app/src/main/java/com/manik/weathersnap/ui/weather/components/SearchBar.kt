package com.manik.weathersnap.ui.weather.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.manik.weathersnap.ui.theme.SkyBlue
import com.manik.weathersnap.ui.theme.SoftBlue

/**
 * Professional search bar with a high-density layout and premium weather aesthetic.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(56.dp),
        placeholder = { 
            Text(
                "Search city...", 
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.4f)
            ) 
        },
        leadingIcon = { 
            Icon(
                Icons.Default.Search, 
                contentDescription = null, 
                tint = SkyBlue,
                modifier = Modifier.size(24.dp)
            ) 
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        Icons.Default.Clear, 
                        contentDescription = "Clear", 
                        tint = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SoftBlue.copy(alpha = 0.5f),
            unfocusedContainerColor = SoftBlue.copy(alpha = 0.5f),
            disabledContainerColor = SoftBlue.copy(alpha = 0.5f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = SkyBlue,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        textStyle = MaterialTheme.typography.bodyLarge
    )
}
