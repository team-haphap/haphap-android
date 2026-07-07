package com.haphap.app.data.model.search

data class JobListModel(
    val id: Int,
    val imageUrl: String,
    val category: String,
    val stage: String,
    val dDay: Int,
    val title: String,
    val content: String,
)
