package com.haphap.app.data.mapper.search

import com.haphap.app.data.local.database.RecentSearchEntity
import com.haphap.app.data.model.search.RecentSearchItemModel
import com.haphap.app.data.model.search.TrendJobItemModel
import com.haphap.app.data.remote.dto.search.PopularItemDto
import com.haphap.app.data.remote.dto.search.PopularListResponseDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun RecentSearchEntity.toModel(): RecentSearchItemModel = RecentSearchItemModel(
    id = id,
    keyword = searchText,
    date = SimpleDateFormat("MM.dd", Locale.getDefault()).format(Date(searchedAt)),
)


fun PopularListResponseDto.toModel(): List<TrendJobItemModel> = postings.map { it.toModel() }

fun PopularItemDto.toModel(): TrendJobItemModel = TrendJobItemModel(
    id = id,
    imageUrl = imageUrl,
    category = category,
    nextStage = nextStage,
    dDay = daysUntilNextStage,
    title = title,
    companyName = companyName,
)
