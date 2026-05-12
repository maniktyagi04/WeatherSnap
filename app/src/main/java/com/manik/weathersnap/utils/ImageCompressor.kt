package com.manik.weathersnap.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageCompressor @Inject constructor() {

    fun compressImage(
        context: Context,
        imageUri: Uri,
        compressionQuality: Int = 60
    ): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            val compressedFile = File(
                context.externalCacheDir,
                "compressed_${System.currentTimeMillis()}.jpg"
            )

            val outputStream = FileOutputStream(compressedFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, outputStream)
            outputStream.flush()
            outputStream.close()

            compressedFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getFileSize(file: File): String {
        val bytes = file.length()
        return formatFileSize(bytes)
    }

    fun getFileSizeFromUri(context: Context, uri: Uri): String {
        return try {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
            val bytes = parcelFileDescriptor?.statSize ?: 0L
            parcelFileDescriptor?.close()
            formatFileSize(bytes)
        } catch (e: Exception) {
            "0 KB"
        }
    }

    private fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "0 KB"
        val kb = bytes / 1024.0
        val mb = kb / 1024.0
        return if (mb >= 1) {
            String.format("%.1f MB", mb)
        } else {
            String.format("%.0f KB", kb)
        }
    }
}
