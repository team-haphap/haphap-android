package com.haphap.app.data.model.search

data class TrendJobListModel(
    val id: Int,
    val imageUrl: String,
    val text: String,
    val stage: String,
    val dDay: Int,
    val company: String,
    val description: String,
)
