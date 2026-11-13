package com.haphap.app.data.model.scrap

data class ScrapModel(
    val postingId: Int,
    val logoImageUrl: String,
    val companyName: String,
    val category: String,
    val title: String,
    val isScrapped: Boolean = true,
)
