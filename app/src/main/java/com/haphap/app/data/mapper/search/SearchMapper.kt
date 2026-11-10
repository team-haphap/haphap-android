package com.haphap.app.data.mapper.search

import com.haphap.app.data.local.database.RecentSearchEntity
import com.haphap.app.data.model.search.PopularSearchItemModel
import com.haphap.app.data.model.search.RangeModel
import com.haphap.app.data.model.search.RecentSearchItemModel
import com.haphap.app.data.model.search.RelatedKeywordItemModel
import com.haphap.app.data.model.search.SearchAutoCompleteItemModel
import com.haphap.app.data.model.search.SearchResultItemModel
import com.haphap.app.data.model.search.SearchResultPageModel
import com.haphap.app.data.model.search.SearchingModel
import com.haphap.app.data.remote.dto.search.AutoCompleteShortcutDto
import com.haphap.app.data.remote.dto.search.HighlightRangeDto
import com.haphap.app.data.remote.dto.search.PopularKeywordDto
import com.haphap.app.data.remote.dto.search.PopularKeywordListResponseDto
import com.haphap.app.data.remote.dto.search.RelatedKeywordDto
import com.haphap.app.data.remote.dto.search.SearchResultItemDto
import com.haphap.app.data.remote.dto.search.SearchResultListResponseDto
import com.haphap.app.data.remote.dto.search.SearchingListResponseDto
import kotlinx.collections.immutable.toImmutableList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun RecentSearchEntity.toModel(): RecentSearchItemModel = RecentSearchItemModel(
    id = id,
    keyword = searchText,
    date = SimpleDateFormat("MM.dd", Locale.getDefault()).format(Date(searchedAt)),
)


fun PopularKeywordListResponseDto.toModel(): List<PopularSearchItemModel> = keywords.map { it.toModel() }

fun PopularKeywordDto.toModel(): PopularSearchItemModel = PopularSearchItemModel(
    rank = rank,
    keyword = keyword,
)

fun SearchResultListResponseDto.toModel(): SearchResultPageModel = SearchResultPageModel(
    results = postings.map { it.toModel() }.toImmutableList(),
    page = page,
    size = size,
    hasNext = hasNext,
)

fun SearchResultItemDto.toModel(): SearchResultItemModel = SearchResultItemModel(
    id = postingId,
    imageUrl = imageUrl,
    companyName = companyName,
    category = categoryName,
    stage = nextStage ?: "",
    dDay = dDay,
    title = title,
)


fun SearchingListResponseDto.toModel(): SearchingModel = SearchingModel(
    relatedPostings = relatedPostings.map { it.toModel() }.toImmutableList(),
    relatedKeywords = relatedKeywords.map { it.toModel() }.toImmutableList(),
)

fun AutoCompleteShortcutDto.toModel(): SearchAutoCompleteItemModel = SearchAutoCompleteItemModel(
    id = postingId,
    imageUrl = imageUrl,
    text = name,
    highlightLength = highlightRanges.firstOrNull()?.toModel() ?: RangeModel(0, 0),
)

fun RelatedKeywordDto.toModel(): RelatedKeywordItemModel = RelatedKeywordItemModel(
    id = keywordId,
    text = name,
    highlightLength = highlightRanges.firstOrNull()?.toModel() ?: RangeModel(0, 0),
)

fun HighlightRangeDto.toModel(): RangeModel = RangeModel(
    start = start,
    end = end,
)
