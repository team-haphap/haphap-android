package com.haphap.app.core.image

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class ImageFileInfo(
    val name: String,
    val sizeBytes: Long,
    val mimeType: String,
)

class ImageFileInfoReader @Inject constructor(
    @param: ApplicationContext private val context: Context,
) {
    suspend fun read(uri: Uri): ImageFileInfo? =
        withContext(Dispatchers.IO) {
            runCatching {
                val resolver = context.contentResolver
                val projection = arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE)

                resolver.query(uri, projection, null, null, null)?.use { cursor ->
                    if (!cursor.moveToFirst()) return@use null

                    ImageFileInfo(
                        name = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)),
                        sizeBytes = cursor.getLong(cursor.getColumnIndexOrThrow(OpenableColumns.SIZE)),
                        mimeType = resolver.getType(uri).orEmpty(),
                    )
                }
            }.getOrNull()
        }
}
