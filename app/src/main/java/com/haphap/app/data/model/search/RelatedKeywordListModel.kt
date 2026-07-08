package com.haphap.app.data.model.search

data class RelatedKeywordListModel(
    val id: Int,
    val text: String,
    val highlightLength: RangeModel,
)
