package com.haphap.app.data.remote.dto.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeBannerListDto (
    @SerialName("banners")
    val banners: List<HomeBannerItemDto>,
)

@Serializable
data class HomeBannerItemDto (
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("displayOrder")
    val displayOrder: Int,
)

