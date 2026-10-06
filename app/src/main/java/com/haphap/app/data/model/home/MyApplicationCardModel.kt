package com.haphap.app.data.model.home

import androidx.compose.runtime.Immutable

@Immutable
data class MyApplicationCardModel (
    val id: Int,
    val logoImageUrl: String,
    val companyName: String,
    val title: String,
    val category: String?,
    val currentStageStatus: String,
    val dDayLabel: String,
)
