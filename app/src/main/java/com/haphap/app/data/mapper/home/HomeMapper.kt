package com.haphap.app.data.mapper.home

import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.CountCardModel
import com.haphap.app.data.model.home.MyApplicationCardModel
import com.haphap.app.data.model.home.PopularJobCardModel
import com.haphap.app.data.model.home.RecentCardModel
import com.haphap.app.data.model.home.RecentJobCardModel
import com.haphap.app.data.model.home.TodayExpectedCardModel
import com.haphap.app.data.remote.dto.home.HomeAnnouncementsDto
import com.haphap.app.data.remote.dto.home.HomeBannerItemDto
import com.haphap.app.data.remote.dto.home.HomeMyApplicationDto
import com.haphap.app.data.remote.dto.home.HomePopularDto
import com.haphap.app.data.remote.dto.home.HomeRecentDto
import com.haphap.app.data.remote.dto.home.HomeRecentPostingsDto
import com.haphap.app.data.remote.dto.home.HomeTodayResponseDto

fun HomeBannerItemDto.toModel(): BannerItemModel =
    BannerItemModel(
        id = displayOrder,
        imageUrl = imageUrl,
        linkUrl = linkUrl,
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
        nextStage = nextStage ?: "",
        dayUntilNextStage = daysUntilNextStage,
    )

fun HomeRecentDto.toModel(): RecentJobCardModel =
    RecentJobCardModel(
        id = postingId,
        logoImageUrl = logoImageUrl,
        title = title,
        position = position,
        nextStage = nextStage ?: "",
        dDayLabel = dDayLabel ?: "",
        companyName = companyName,
    )

fun HomePopularDto.toModel(): PopularJobCardModel =
    PopularJobCardModel(
        id = postingId,
        logoImageUrl = logoImageUrl,
        title = title,
        position = position,
        nextStage = nextStage ?: "",
        dDayLabel = dDayLabel ?: "",
        companyName = companyName,
    )

fun HomeMyApplicationDto.toModel(): MyApplicationCardModel =
    MyApplicationCardModel(
        id = postingId,
        logoImageUrl = logoImageUrl,
        title = title,
        position = position,
        currentStageStatus = currentStageStatus ?: "",
        dDayLabel = dDayLabel ?: "",
        companyName = companyName,
    )
