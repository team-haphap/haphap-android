package com.haphap.app.data.model.list

data class JobItemModel(
    val id: Int,
    val imageUrl: String,
    val category: String,
    val stage: String,
    val dDay: Int,
    val title: String,
    val content: String,
)
