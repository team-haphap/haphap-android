package com.haphap.app.core.image

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ImageMultipartConverter @Inject constructor(
    @param: ApplicationContext private val context: Context,
) {
    suspend fun toPart(uri: Uri, partName: String): MultipartBody.Part =
        withContext(Dispatchers.IO) {
            val resolver = context.contentResolver
            val mimeType = resolver.getType(uri) ?: DEFAULT_MIME_TYPE
            val bytes = resolver.openInputStream(uri)?.use { it.readBytes() }
                ?: throw IllegalStateException("Cannot open image: $uri")
            val extension = MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(mimeType) ?: DEFAULT_EXTENSION

            MultipartBody.Part.createFormData(
                name = partName,
                filename = "$FILE_NAME_PREFIX${System.currentTimeMillis()}.$extension",
                body = bytes.toRequestBody(mimeType.toMediaType()),
            )
        }

    companion object {
        private const val DEFAULT_MIME_TYPE = "image/jpeg"
        private const val DEFAULT_EXTENSION = "jpg"
        private const val FILE_NAME_PREFIX = "image_"
    }
}
