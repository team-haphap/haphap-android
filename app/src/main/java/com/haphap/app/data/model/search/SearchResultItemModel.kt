package com.haphap.app.data.model.search

data class SearchResultItemModel(
    val id: Int,
    val imageUrl: String,
    val companyName: String,
    val category: String,
    val stage: String,
    val dDay: Int,
    val title: String,
)
