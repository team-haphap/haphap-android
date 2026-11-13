package com.haphap.app.data.model.myapplication

import androidx.compose.runtime.Immutable

@Immutable
data class MyApplicationModel(
    val postingId: Int,
    val companyName: String,
    val title: String,
    val logoImageUrl: String,
    val currentStage: String,
    val isClosed: Boolean,
    val result: String,
)
