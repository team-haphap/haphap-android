package com.haphap.app.data.repository.impl.alarm

import com.haphap.app.core.fcm.FirebaseMessagingManager
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalFcmDataSource
import com.haphap.app.data.remote.datasource.api.alarm.AlarmDataSource
import com.haphap.app.data.remote.dto.alarm.AlarmDeviceRequestDto
import com.haphap.app.data.repository.api.alarm.AlarmRepository
import jakarta.inject.Inject
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDatasource: AlarmDataSource,
    private val firebaseMessagingManager: FirebaseMessagingManager,
    private val localFcmDataSource: LocalFcmDataSource,
) : AlarmRepository {

    private val registerMutex = Mutex()

    override suspend fun registerDeviceId(): Result<Unit> {

        return suspendRunCatching {
            val fcmToken = firebaseMessagingManager.getFcmToken()
                ?: throw IllegalStateException("FCM token is null")

            registerMutex.withLock {
                postAlarmDevice(fcmToken)
            }
        }.onSuccess {
            Timber.tag(TAG).d("디바이스 등록 성공")
        }.onFailure {
            Timber.tag(TAG).e(it, "디바이스 등록 실패")
        }
    }

    override suspend fun updateFcmToken(newFcmToken: String): Result<Unit> {

        return suspendRunCatching {
            registerMutex.withLock {
                postAlarmDevice(newFcmToken)
            }
        }.onSuccess {
            Timber.tag(TAG).d("FCM 토큰 재발급 성공")
        }.onFailure {
            Timber.tag(TAG).e(it, "FCM 토큰 재발급 실패")
        }
    }

    private suspend fun postAlarmDevice(fcmToken: String) {
        val savedToken = localFcmDataSource.getFcmToken()

        if (savedToken == fcmToken) {
            Timber.tag(TAG).d("이미 저장된 토큰입니다")
            return
        }

        val deviceId = firebaseMessagingManager.getInstallationId()
            ?: throw IllegalStateException("Device id is null")

        alarmDatasource.postAlarmDevice(
            AlarmDeviceRequestDto(
                deviceId = deviceId,
                fcmToken = fcmToken,
                deviceType = DEVICE_TYPE,
            )
        )

        localFcmDataSource.setFcmToken(fcmToken)
    }

    companion object {
        private const val TAG = "FCM"
        private const val DEVICE_TYPE = "ANDROID"
    }
}
