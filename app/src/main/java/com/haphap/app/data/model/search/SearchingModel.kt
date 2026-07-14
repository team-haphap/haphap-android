package com.haphap.app.data.model.search

import kotlinx.collections.immutable.ImmutableList

data class SearchingModel (
    val relatedPostings: ImmutableList<SearchAutoCompleteItemModel>,
    val relatedKeywords: ImmutableList<RelatedKeywordItemModel>
)
