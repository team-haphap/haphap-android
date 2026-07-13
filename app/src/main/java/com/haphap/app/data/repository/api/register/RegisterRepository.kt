package com.haphap.app.data.repository.api.register

import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.data.model.register.RegistrationCheckModel
import com.haphap.app.data.model.register.RegistrationModel
import com.haphap.app.presentation.register.type.RegisterResultType

interface RegisterRepository {
    suspend fun getPostingNames(): Result<List<RegisterDropDownItemModel>>
    suspend fun getPostingStages(postingId: Int): Result<List<RegisterProcessModel>>
    suspend fun postRegistration(registerInfo: RegisterModel): Result<RegistrationModel>
    suspend fun checkRegistration(postingId: Int, stageId: Int, result: RegisterResultType): Result<RegistrationCheckModel>
}