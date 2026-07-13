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
import com.haphap.app.data.repository.api.register.RegisterRepository
import com.haphap.app.presentation.register.type.RegisterResultType
import retrofit2.HttpException
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val postingDataSource: RegisterDataSource,
) : RegisterRepository {

    override suspend fun getPostingNames(): Result<List<RegisterDropDownItemModel>> =
        suspendRunCatching {
            postingDataSource.getPostingNames().checkData().postings.map { it.toModel() }
        }

    override suspend fun getPostingStages(postingId: Int): Result<List<RegisterProcessModel>> =
        suspendRunCatching {
            postingDataSource.getPostingStages(postingId).checkData().stages.map { it.toModel() }
        }
    
    override suspend fun postRegistration(registerInfo: RegisterModel): Result<RegistrationModel> =
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

            postingDataSource.postRegistration(request).checkData().toModel()
        }

    override suspend fun checkRegistration(postingId: Int, stageId: Int): Result<RegistrationCheckModel> =
        suspendRunCatching {
            try {
                val response = postingDataSource.getRegistrationCheck(postingId, stageId)
                when (response.code) {
                    REGISTRATION_CONFIRM_REQUIRED_CODE -> RegistrationCheckModel.CONFIRM_REQUIRED
                    else -> RegistrationCheckModel.NEW
                }
            } catch (e: HttpException) {
                if (e.code() == HTTP_CONFLICT) {
                    RegistrationCheckModel.DUPLICATE
                } else {
                    throw e
                }
            }
        }

    companion object {
        private const val REGISTRATION_CONFIRM_REQUIRED_CODE = "REGISTRATION_CONFIRM_REQUIRED"
        private const val HTTP_CONFLICT = 409
    }
}