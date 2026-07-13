package com.haphap.app.data.model.register

import com.haphap.app.presentation.register.type.NotificationChannelType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RegisterModel(
    val postingId: Int? = null,
    val stageId: Int? = null,
    val result: RegisterResultType? = null,
    val contactedDate: String? = null,
    val contactedTime: String? = null,
    val contactedMethod: ImmutableList<NotificationChannelType> = persistentListOf(),
    val anonymous: Boolean = false,
    val alarmEnabled: Boolean = false,
)