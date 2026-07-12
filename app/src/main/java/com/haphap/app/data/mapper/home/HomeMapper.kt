package com.haphap.app.data.mapper.home

import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.CountCardModel
import com.haphap.app.data.remote.dto.home.HomeBannerItemDto
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