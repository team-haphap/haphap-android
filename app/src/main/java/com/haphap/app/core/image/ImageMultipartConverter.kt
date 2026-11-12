package com.haphap.app.core.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.webkit.MimeTypeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class ImageMultipartConverter @Inject constructor(
    @param: ApplicationContext private val context: Context,
) {
    suspend fun toPart(uri: Uri, partName: String): MultipartBody.Part =
        withContext(Dispatchers.IO) {
            val resolver = context.contentResolver
            val originalMimeType = resolver.getType(uri) ?: DEFAULT_MIME_TYPE

            val (bytes, mimeType) = if (originalMimeType in HEIF_MIME_TYPES) {
                convertToJpeg(uri) to DEFAULT_MIME_TYPE
            } else {
                val originalBytes = resolver.openInputStream(uri)?.use { it.readBytes() }
                    ?: throw IllegalStateException("Cannot open image: $uri")
                originalBytes to originalMimeType
            }

            val extension = MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(mimeType) ?: DEFAULT_EXTENSION

            MultipartBody.Part.createFormData(
                name = partName,
                filename = "$FILE_NAME_PREFIX${System.currentTimeMillis()}.$extension",
                body = bytes.toRequestBody(mimeType.toMediaType()),
            )
        }

    private fun convertToJpeg(uri: Uri): ByteArray {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        val bitmap = ImageDecoder.decodeBitmap(source)
        return try {
            ByteArrayOutputStream().use { output ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, output)
                output.toByteArray()
            }
        } finally {
            bitmap.recycle()
        }
    }

    companion object {
        private const val DEFAULT_MIME_TYPE = "image/jpeg"
        private const val DEFAULT_EXTENSION = "jpg"
        private const val FILE_NAME_PREFIX = "image_"
        private const val JPEG_QUALITY = 90
        private val HEIF_MIME_TYPES = setOf("image/heic", "image/heif")
    }
}
