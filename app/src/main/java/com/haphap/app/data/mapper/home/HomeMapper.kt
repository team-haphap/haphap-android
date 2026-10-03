package com.haphap.app.data.mapper.home

import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.MyApplicationCardModel
import com.haphap.app.data.model.home.PopularJobCardModel
import com.haphap.app.data.model.home.RecentJobCardModel
import com.haphap.app.data.remote.dto.home.HomeBannerItemDto
import com.haphap.app.data.remote.dto.home.HomeMyApplicationDto
import com.haphap.app.data.remote.dto.home.HomePopularDto
import com.haphap.app.data.remote.dto.home.HomeRecentDto

fun HomeBannerItemDto.toModel(): BannerItemModel =
    BannerItemModel(
        id = displayOrder,
        imageUrl = imageUrl,
        linkUrl = linkUrl,
    )

fun HomeRecentDto.toModel(): RecentJobCardModel =
    RecentJobCardModel(
        id = postingId,
        logoImageUrl = logoImageUrl,
        title = title,
        category = category ?: "",
        nextStage = nextStage ?: "",
        dDayLabel = dDayLabel ?: "",
        companyName = companyName,
    )

fun HomePopularDto.toModel(): PopularJobCardModel =
    PopularJobCardModel(
        id = postingId,
        logoImageUrl = logoImageUrl,
        title = title,
        category = category ?: "",
        nextStage = nextStage ?: "",
        dDayLabel = dDayLabel ?: "",
        companyName = companyName,
    )

fun HomeMyApplicationDto.toModel(): MyApplicationCardModel =
    MyApplicationCardModel(
        id = postingId,
        logoImageUrl = logoImageUrl,
        title = title,
        category = category?: "",
        currentStageStatus = currentStageStatus ?: "",
        dDayLabel = dDayLabel ?: "",
        companyName = companyName,
    )
