package com.haphap.app.core.image

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 * Bitmap을 기기 갤러리(Pictures/HapHap)에 PNG로 저장합니다.
 *
 * Android 9(API 28) 이하에서는 WRITE_EXTERNAL_STORAGE 권한이 허용된 상태에서 호출해야 합니다.
 */

class ImageSaver @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    suspend fun saveToGallery(bitmap: Bitmap, fileName: String): Unit = withContext(Dispatchers.IO) {
        val resolver = context.contentResolver
        val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val imageDetail = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, MIME_TYPE_PNG)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/$DIRECTORY_NAME")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val imageUri = resolver.insert(imageCollection,imageDetail) ?: throw IOException("MediaStore 이미지 항목 생성 실패")

        try {
            val softwareBitmap = if (bitmap.config == Bitmap.Config.HARDWARE) {
                bitmap.copy(Bitmap.Config.ARGB_8888, false)
            } else {
                bitmap
            }

            resolver.openOutputStream(imageUri)?.use { outputStream ->
                if (!softwareBitmap.compress(Bitmap.CompressFormat.PNG, PNG_QUALITY, outputStream)) {
                    throw IOException("비트맵 압축 실패")
                }
            } ?: throw IOException("출력 스트림 열기 실패")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                imageDetail.clear()
                imageDetail.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(imageUri, imageDetail, null, null)
            }
        } catch (e: Exception) {
            resolver.delete(imageUri, null, null)
            throw e
        }
    }

    companion object {
        private const val DIRECTORY_NAME = "HapHap"
        private const val MIME_TYPE_PNG = "image/png"
        private const val PNG_QUALITY = 100 // png라 무시됨
    }
}
