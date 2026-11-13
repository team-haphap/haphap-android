package com.haphap.app.data.model.myapplication

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class MyApplicationListModel(
    val inProgressCount: Int,
    val closedCount: Int,
    val applicationList: ImmutableList<MyApplicationModel>,
)
