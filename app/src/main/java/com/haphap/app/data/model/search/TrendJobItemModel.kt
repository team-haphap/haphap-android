package com.haphap.app.data.model.search

data class TrendJobItemModel(
    val id: Int,
    val imageUrl: String,
    val category: String,
    val nextStage: String,
    val dDay: Int,
    val companyName: String,
    val title: String,
)
