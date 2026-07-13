package com.haphap.app.data.repository.impl.register

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.register.toModel
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.data.model.register.RegistrationModel
import com.haphap.app.data.remote.datasource.api.register.RegisterDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.remote.dto.register.RegisterRequestDto
import com.haphap.app.data.repository.api.register.RegisterRepository
import com.haphap.app.presentation.register.type.RegisterResultType
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
}