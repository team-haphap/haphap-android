package com.haphap.app.presentation.main

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.fcm.HapHapFirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { }

    private var extractedPostingId by mutableStateOf<Int?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        askNotificationPermission()
        extractedPostingId = extractPostingId(intent)

        setContent {
            HapHapTheme {
                MainScreen(
                    extractedPostingId = extractedPostingId,
                    resetExtractedPostingId = { extractedPostingId = null },
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        extractedPostingId = extractPostingId(intent)
    }

    private fun extractPostingId(intent: Intent): Int? {
        val postingId = intent.getIntExtra(HapHapFirebaseMessagingService.MESSAGE_POSTING_ID, -1)
        return postingId.takeIf { it != -1 }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}
