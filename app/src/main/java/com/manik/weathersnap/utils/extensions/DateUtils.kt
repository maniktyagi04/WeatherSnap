package com.manik.weathersnap.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Formats a long timestamp into a human-readable date string.
 * Format: dd MMM yyyy, HH:mm
 */
fun Long.toFormattedDate(): String {
    val dateFormatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return dateFormatter.format(Date(this))
}
