package com.haphap.app.core.fcm

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber

class FirebaseMessagingManager {

    fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.tag(TAG).e("Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            // token 받아오기
            val token = task.result
            Timber.tag(TAG).d("토큰 : $token")

            // 서버 토큰 전송

        })
    }

    companion object {
        private const val TAG = "FCM"
    }
}
