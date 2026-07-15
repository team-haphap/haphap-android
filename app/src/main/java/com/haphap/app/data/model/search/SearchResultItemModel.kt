package com.haphap.app.data.model.search

import kotlinx.collections.immutable.ImmutableList

data class SearchResultPageModel(
    val results: ImmutableList<SearchResultItemModel>,
    val page: Int,
    val size: Int,
    val hasNext: Boolean,
)

data class SearchResultItemModel(
    val id: Int,
    val imageUrl: String,
    val companyName: String,
    val category: String,
    val stage: String,
    val dDay: Int,
    val title: String,
)
