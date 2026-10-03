package com.haphap.app.data.model.home

data class MyApplicationCardModel (
    val id: Int,
    val logoImageUrl: String,
    val companyName: String,
    val title: String,
    val category: String?,
    val currentStageStatus: String,
    val dDayLabel: String,
)
