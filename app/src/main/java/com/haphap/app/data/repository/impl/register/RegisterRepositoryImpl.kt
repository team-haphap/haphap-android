package com.haphap.app.data.repository.impl.register

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.register.toModel
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.data.model.register.RegistrationCheckModel
import com.haphap.app.data.model.register.RegistrationModel
import com.haphap.app.data.remote.datasource.api.register.RegisterDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.remote.dto.register.RegisterRequestDto
import com.haphap.app.data.remote.dto.register.RegistrationCheckRequestDto
import com.haphap.app.data.repository.api.register.RegisterRepository
import com.haphap.app.presentation.register.type.RegisterResultType
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val postingDataSource: RegisterDataSource,
) : RegisterRepository {

    override suspend fun getRegisterPostNames(): Result<List<RegisterDropDownItemModel>> =
        suspendRunCatching {
            postingDataSource.getRegisterPostNames().checkData().postings.map { it.toModel() }
        }

    override suspend fun getRegisterPostStages(postingId: Int): Result<List<RegisterProcessModel>> =
        suspendRunCatching {
            postingDataSource.getRegisterPostStages(postingId).checkData().stages.map { it.toModel() }
        }
    
    override suspend fun postRegister(registerInfo: RegisterModel): Result<RegistrationModel> =
        suspendRunCatching {
            val postingId = requireNotNull(registerInfo.postingId) { "postingId is required" }
            val stageId = requireNotNull(registerInfo.stageId) { "stageId is required" }
            val result = requireNotNull(registerInfo.result) { "result is required" }

            val isPending = result == RegisterResultType.PENDING

            val request = RegisterRequestDto(
                postingId = postingId,
                stageId = stageId,
                contactedDate = if (isPending) null else registerInfo.contactedDate,
                contactedTime = if (isPending) null else registerInfo.contactedTime,
                contactMethods = if (isPending) null else registerInfo.contactedMethod.map { it.name },
                result = result.name,
                anonymous = registerInfo.anonymous,
                alarmEnabled = registerInfo.alarmEnabled,
            )

            postingDataSource.postRegister(request).checkData().toModel()
        }

    override suspend fun postCheckRegistration(
        postingId: Int,
        stageId: Int,
        result: RegisterResultType,
    ): Result<RegistrationCheckModel> =
        suspendRunCatching {
            try {
                val request = RegistrationCheckRequestDto(result = result.name)
                val response = postingDataSource.getRegisterCheck(postingId, stageId, request)
                when (response.code) {
                    REGISTRATION_CONFIRM_REQUIRED_CODE -> RegistrationCheckModel.CONFIRM_REQUIRED
                    else -> RegistrationCheckModel.NEW
                }
            } catch (e: HttpException) {
                if (e.code() == HTTP_CONFLICT) {
                    RegistrationCheckModel.DUPLICATE
                } else {
                    Timber.e(e, "checkRegistration failed - HTTP ${e.code()}")
                    throw e
                }
            }
        }

    companion object {
        private const val REGISTRATION_CONFIRM_REQUIRED_CODE = "REGISTRATION_CONFIRM_REQUIRED"
        private const val HTTP_CONFLICT = 409
    }
}