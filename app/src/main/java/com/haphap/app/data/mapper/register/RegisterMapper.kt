package com.haphap.app.data.mapper.register

import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.data.model.register.RegistrationModel
import com.haphap.app.data.remote.dto.register.RegisterNameDto
import com.haphap.app.data.remote.dto.register.RegisterStageDto
import com.haphap.app.data.remote.dto.register.RegistrationResponseDto

fun RegisterNameDto.toModel(): RegisterDropDownItemModel = RegisterDropDownItemModel(
    id = id,
    text = title,
)

fun RegisterStageDto.toModel(): RegisterProcessModel = RegisterProcessModel(
    id = stageId,
    text = stageName,
)

fun RegistrationResponseDto.toModel(): RegistrationModel = RegistrationModel(
    registrationId = registrationId,
    verificationStatus = verificationStatus,
)
