package com.haphap.app.data.mapper.scrap

import com.haphap.app.data.model.scrap.ScrapModel
import com.haphap.app.data.remote.dto.scrap.ScrapItemResponseDto
import com.haphap.app.data.remote.dto.scrap.ScrapListResponseDto

fun ScrapListResponseDto.toModels(): List<ScrapModel> = scraps.map { it.toModel() }

fun ScrapItemResponseDto.toModel(): ScrapModel =
    ScrapModel(
        postingId = postingId,
        logoImageUrl = logoImageUrl.orEmpty(),
        companyName = companyName,
        category = category,
        title = title,
    )
