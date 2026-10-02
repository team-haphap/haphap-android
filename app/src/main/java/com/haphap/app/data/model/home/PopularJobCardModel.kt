package com.haphap.app.data.model.home

data class PopularJobCardModel (
    val id: Int,
    val logoImageUrl: String,
    val companyName: String,
    val title: String,
    val position: String,
    val nextStage: String,
    val dDayLabel: String,
)