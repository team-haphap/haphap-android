package com.haphap.app.data.repository.impl.mypage

import android.content.Context
import android.net.Uri
import com.haphap.app.core.fcm.FirebaseMessagingManager
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalFcmDataSource
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import com.haphap.app.data.mapper.mypage.toModel
import com.haphap.app.data.mapper.mypage.toProfileModel
import com.haphap.app.data.model.mypage.AccountInfoModel
import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.model.mypage.MyPageProfileModel
import com.haphap.app.data.remote.datasource.api.mypage.MyPageDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.remote.dto.mypage.LeaveRequestDto
import com.haphap.app.data.remote.dto.mypage.LogoutRequestDto
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import timber.log.Timber

class MyPageRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val myPageDataSource: MyPageDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
    private val localFcmDataSource: LocalFcmDataSource,
    private val firebaseMessagingManager: FirebaseMessagingManager,
) : MyPageRepository {
    override suspend fun getMyPage(): Result<MyPageModel> =
        suspendRunCatching {
            val response = myPageDataSource.getMyPage().checkData()
            response.toModel()
        }

    override suspend fun getAccountInfo(): Result<AccountInfoModel> =
        suspendRunCatching {
            val response = myPageDataSource.getAccountInfo().checkData()
            response.toModel()
        }

    override suspend fun getProfile(): Result<MyPageProfileModel> =
        suspendRunCatching {
            val response = myPageDataSource.getMyPage().checkData()
            response.toProfileModel()
        }

    override suspend fun getNicknameValidation(nickname: String): Result<Boolean> =
        suspendRunCatching {
            val response = myPageDataSource.getNicknameValidation(nickname)

            if (!response.isSuccessful && response.code() != 409) {
                throw HttpException(response)
            }

            response.isSuccessful
        }

    override suspend fun patchProfile(nickname: String?, profileImageUri: Uri?): Result<Unit> =
        suspendRunCatching {
            myPageDataSource.patchProfile(
                nickname = nickname?.toRequestBody("text/plain".toMediaType()),
                profileImage = profileImageUri?.toProfileImagePart(),
            ).checkData()
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
                    if (!response.isSuccessful && response.code() != 401) throw HttpException(
                        response
                    )
                } else {
                    Timber.w("Installation id is null. Skip remote logout")
                }
            }

            localTokenDataSource.clearTokens()
        }

    private suspend fun Uri.toProfileImagePart(): MultipartBody.Part =
        withContext(Dispatchers.IO) {
            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(this@toProfileImagePart) ?: "image/jpeg"
            val bytes = contentResolver.openInputStream(this@toProfileImagePart)
                ?.use { it.readBytes() }
                ?: throw IllegalStateException("이미지를 읽을 수 없습니다")
            val extension = if (mimeType == "image/png") "png" else "jpg"

            MultipartBody.Part.createFormData(
                name = "profileImage",
                filename = "profile.$extension",
                body = bytes.toRequestBody(mimeType.toMediaType()),
            )
        }
}
