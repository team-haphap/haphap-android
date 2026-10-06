package com.haphap.app.data.repository.impl.mypage

import com.haphap.app.core.fcm.FirebaseMessagingManager
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalFcmDataSource
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import com.haphap.app.data.mapper.mypage.toModel
import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.remote.datasource.api.mypage.MyPageDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.remote.dto.mypage.LeaveRequestDto
import com.haphap.app.data.remote.dto.mypage.LogoutRequestDto
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import jakarta.inject.Inject
import retrofit2.HttpException
import timber.log.Timber

class MyPageRepositoryImpl @Inject constructor(
    private val myPageDataSource: MyPageDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
    private val localFcmDataSource: LocalFcmDataSource,
    private val firebaseMessagingManager: FirebaseMessagingManager,
): MyPageRepository {
    override suspend fun getMyPage(): Result<MyPageModel> =
        suspendRunCatching {
            val response = myPageDataSource.getMyPage().checkData()
            response.toModel()
        }

    override suspend fun deleteMember(reason: String, etcReason: String?): Result<Unit> =
        suspendRunCatching {
            val response = myPageDataSource.deleteMember(
                LeaveRequestDto(reason = reason, etcReason = etcReason),
            )

            if (!response.isSuccessful && response.code() != 404) throw HttpException(response)

            localTokenDataSource.clearTokens()
        }

    override suspend fun postLogout(): Result<Unit> =
        suspendRunCatching {
            val accessToken = localTokenDataSource.getAccessToken()

            if (!accessToken.isNullOrBlank()) {
                val deviceId = firebaseMessagingManager.getInstallationId()

                if (deviceId != null) {
                    val response = myPageDataSource.postLogout(LogoutRequestDto(deviceId))
                    if (!response.isSuccessful && response.code() != 401) throw HttpException(response)
                } else {
                    Timber.w("Installation id is null. Skip remote logout")
                }
            }

            localTokenDataSource.clearTokens()
        }
}