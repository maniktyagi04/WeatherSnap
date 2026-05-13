package com.manik.weathersnap.ui.report.components

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.manik.weathersnap.ui.theme.SkyBlue

/**
 * Premium image preview component with size metadata and smooth transitions.
 */
@Composable
fun ImagePreview(
    imageUri: Uri?,
    originalSize: String = "",
    compressedSize: String = "",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.05f)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = imageUri,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "ImageTransition"
        ) { uri ->
            if (uri != null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = uri,
                        contentDescription = "Preview",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Metadata Overlay
                    if (originalSize.isNotEmpty() && compressedSize.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(12.dp)
                                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            SizeBadge("ORIGINAL", originalSize)
                            SizeBadge("COMPRESSED", compressedSize)
                        }
                    }
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.AddAPhoto,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color.White.copy(alpha = 0.2f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No image captured",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}

@Composable
private fun SizeBadge(label: String, size: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 9.sp, color = Color.White.copy(alpha = 0.5f), fontWeight = FontWeight.Bold)
        Text(size, fontSize = 12.sp, color = SkyBlue, fontWeight = FontWeight.Black)
    }
}
