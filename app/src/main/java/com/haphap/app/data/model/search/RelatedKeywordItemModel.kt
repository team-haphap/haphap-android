package com.haphap.app.data.model.search

data class RelatedKeywordItemModel(
    val id: Int,
    val text: String,
    val highlightLength: RangeModel,
)
