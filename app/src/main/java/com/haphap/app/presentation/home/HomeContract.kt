package com.haphap.app.presentation.home

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.CountCardModel
import com.haphap.app.data.model.home.RecentCardModel
import com.haphap.app.data.model.home.TodayExpectedCardModel
import com.haphap.app.presentation.common.state.CategoryChipState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface HomeContract {
    @Immutable
    data class State(
        val bannerList: ImmutableList<BannerItemModel> = persistentListOf(),
        val countCardModel: CountCardModel? = null,
        val recentCardList: ImmutableList<RecentCardModel> = persistentListOf(),
        val todayExpectedCardList: ImmutableList<TodayExpectedCardModel> = persistentListOf(),
        val categoryChipState: CategoryChipState = CategoryChipState(),
    )
}
