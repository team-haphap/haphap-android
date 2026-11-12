package com.haphap.app.data.mapper.passcard

import com.haphap.app.data.model.passcard.PassCardDetailModel
import com.haphap.app.data.model.passcard.PassCardStorageModel
import com.haphap.app.data.remote.dto.passcard.PassCardDetailResponseDto
import com.haphap.app.data.remote.dto.passcard.PassCardStorageItemResponseDto

fun PassCardStorageItemResponseDto.toModel(): PassCardStorageModel =
    PassCardStorageModel(
        id = registrationId,
        imageUrl = companyLogoImageUrl,
        companyName = companyName,
        category = category,
        title = title,
    )

fun PassCardDetailResponseDto.toModel(): PassCardDetailModel =
    PassCardDetailModel(
        id = registrationId,
        userName = userName,
        companyName = companyName,
        recruitName = title,
        logoUrl = companyCardLogoImageUrl,
        backgroundImageUrl = cardImageUrl,
    )

