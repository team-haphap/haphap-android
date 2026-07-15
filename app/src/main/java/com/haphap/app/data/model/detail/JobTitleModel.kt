package com.haphap.app.data.model.detail

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class JobTitleModel(
    val companyName: String = "",
    val postingTitle: String = "",
    val keywords: ImmutableList<String> = persistentListOf(),
    val currentState: String = "",
)