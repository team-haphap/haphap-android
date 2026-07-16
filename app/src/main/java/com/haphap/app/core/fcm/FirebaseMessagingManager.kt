package com.haphap.app.core.fcm

import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseMessagingManager @Inject constructor() {

    suspend fun getFcmToken(): String? = suspendCancellableCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (continuation.isActive) {
                if (!task.isSuccessful) {
                    Timber.tag(TAG).e("FCM Token 불러오기 실패했습니다. ${task.exception}")
                    continuation.resume(null)
                    return@addOnCompleteListener
                }

                val token = task.result
                Timber.tag(TAG).d("토큰 : $token")
                continuation.resume(token)
            }
        }
    }

    suspend fun getInstallationId(): String? = suspendCancellableCoroutine { continuation ->
        FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
            if (continuation.isActive) {
                if (!task.isSuccessful) {
                    Timber.tag(TAG).e("FireBaseInstallations id 불러오기 실패했습니다 : ${task.exception}")
                    continuation.resume(null)
                    return@addOnCompleteListener
                }

                val installationId = task.result
                Timber.tag(TAG).d("device ID : $installationId")
                continuation.resume(installationId)
            }
        }
    }

    companion object {
        private const val TAG = "FCM"
    }
}
