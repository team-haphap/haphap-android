package com.haphap.app.data.repository.api.register

import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.type.RegisterResultType
import com.haphap.app.data.model.register.RegistrationCheckModel
import com.haphap.app.data.model.register.RegistrationModel

interface RegisterRepository {
    suspend fun getRegisterPostNames(): Result<List<RegisterDropDownItemModel>>
    suspend fun getRegisterPostStages(postingId: Int): Result<List<RegisterProcessModel>>
    suspend fun postRegister(registerInfo: RegisterModel): Result<RegistrationModel>
    suspend fun postCheckRegistration(postingId: Int, stageId: Int, result: RegisterResultType): Result<RegistrationCheckModel>
}