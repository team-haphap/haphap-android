package com.haphap.app.data.model.detail

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class JobParticipantModel(
    val registeredCount: Int = 0,
    val profileImages: ImmutableList<String> = persistentListOf(),
    val additionalParticipantCount: Int = 0,
)