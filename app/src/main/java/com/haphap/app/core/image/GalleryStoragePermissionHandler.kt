package com.haphap.app.core.image

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/**
 * 갤러리 저장 권한이 있을 때 작업을 실행하는 함수를 반환합니다.
 *
 * Android 10(API 29) 이상은 권한 없이 바로 실행하고,
 * Android 9 이하는 WRITE_EXTERNAL_STORAGE 권한을 요청한 뒤 허용되면 실행합니다.
 *
 * @param onDenied 권한이 거부되었을 때 실행할 작업
 */

@Composable
fun rememberGalleryStoragePermissionHandler(
    onDenied: () -> Unit,
): (onGranted: () -> Unit) -> Unit {
    val context = LocalContext.current
    val currentOnDenied by rememberUpdatedState(onDenied)
    var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) pendingAction?.invoke() else currentOnDenied()
        pendingAction = null
    }

    return remember(context, launcher) {
        { onGranted ->
            if (context.hasStoragePermission()) {
                onGranted()
            } else {
                pendingAction = onGranted
                launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
}

private fun Context.hasStoragePermission(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ) == PackageManager.PERMISSION_GRANTED
