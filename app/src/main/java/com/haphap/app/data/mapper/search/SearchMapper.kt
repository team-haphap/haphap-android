package com.haphap.app.data.mapper.search

import com.haphap.app.data.local.database.RecentSearchEntity
import com.haphap.app.data.model.search.RecentSearchItemModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun RecentSearchEntity.toModel(): RecentSearchItemModel = RecentSearchItemModel(
    id = id,
    keyword = searchText,
    date = SimpleDateFormat("MM.dd", Locale.getDefault()).format(Date(searchedAt)),
)
