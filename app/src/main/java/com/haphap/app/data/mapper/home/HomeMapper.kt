package com.haphap.app.data.mapper.home

import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.CountCardModel
import com.haphap.app.data.model.home.RecentCardModel
import com.haphap.app.data.model.home.TodayExpectedCardModel
import com.haphap.app.data.remote.dto.home.HomeAnnouncementsDto
import com.haphap.app.data.remote.dto.home.HomeBannerItemDto
import com.haphap.app.data.remote.dto.home.HomeRecentPostingsDto
import com.haphap.app.data.remote.dto.home.HomeTodayResponseDto

fun HomeBannerItemDto.toModel(): BannerItemModel =
    BannerItemModel(
        id = displayOrder,
        imageUrl = imageUrl,
    )

fun HomeTodayResponseDto.toModel(): CountCardModel =
    CountCardModel(
        cumulatedCount = cumulatedCount,
        onGoingCount = onGoingCount,
        announcedCount = announcedCount,
    )

fun HomeAnnouncementsDto.toModel(): TodayExpectedCardModel =
    TodayExpectedCardModel(
        id = id,
        imageUrl = imageUrl,
        companyName = companyName,
        category = category,
        stageName = stageName,
        title = title,
    )

fun HomeRecentPostingsDto.toModel(): RecentCardModel =
    RecentCardModel(
        id = id,
        imageUrl = imageUrl,
        title = title,
        companyName = companyName,
        category = category,
        nextStage = nextStage ?: "null",
        dayUntilNextStage = daysUntilNextStage ?: 0,
        //Todo: 상의 후 null값 변경 예정
    )