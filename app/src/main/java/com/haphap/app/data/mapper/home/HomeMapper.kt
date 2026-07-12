package com.haphap.app.data.mapper.home

import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.remote.dto.home.HomeBannerItemDto

fun HomeBannerItemDto.toModel(): BannerItemModel =
    BannerItemModel(
        id = displayOrder,
        imageUrl = imageUrl,
    )