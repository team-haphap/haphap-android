package com.haphap.app.data.model.register

import com.haphap.app.presentation.register.type.RegisterContactedMethodType
import com.haphap.app.presentation.register.type.RegisterResultType

data class RegisterModel(
    val postingId: Int,
    val stageId: Int,
    val result: RegisterResultType,
    val contactedDate: String,
    val contactedTime: String,
    val contactedMethod: RegisterContactedMethodType,
    val anonymous: Boolean,
    val alarmEnabled: Boolean,
)