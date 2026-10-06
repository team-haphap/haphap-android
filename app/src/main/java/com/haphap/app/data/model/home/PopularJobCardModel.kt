package com.haphap.app.data.model.home

import androidx.compose.runtime.Immutable

@Immutable
data class PopularJobCardModel (
    val id: Int,
    val logoImageUrl: String,
    val companyName: String,
    val title: String,
    val category: String?,
    val nextStage: String?,
    val dDayLabel: String,
)