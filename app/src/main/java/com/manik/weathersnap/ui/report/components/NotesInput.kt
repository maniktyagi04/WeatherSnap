package com.manik.weathersnap.ui.report.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesInput(
    notes: String,
    onNotesChange: (String) -> Unit
) {
    OutlinedTextField(
        value = notes,
        onValueChange = onNotesChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        label = { Text("Observation Notes") },
        placeholder = { Text("Describe the weather conditions...") },
        minLines = 3,
        maxLines = 5,
        shape = MaterialTheme.shapes.medium
    )
}
