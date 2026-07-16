package com.haphap.app.data.model.home

data class RecentCardModel(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val companyName: String,
    val category: String,
    val nextStage: String,
    val dayUntilNextStage: String,
)
