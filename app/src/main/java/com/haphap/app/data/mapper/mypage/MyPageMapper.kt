package com.haphap.app.data.mapper.mypage

import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.model.mypage.MyPageProfileModel
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto

fun MyPageResponseDto.toModel(): MyPageModel =
    MyPageModel(
        name = name,
        anonymousName = anonymousName,
        email = email,
        profileImageUrl = profileImageUrl,
    )

fun MyPageResponseDto.toProfileModel(): MyPageProfileModel =
    MyPageProfileModel(
        nickname = anonymousName.removePrefix(ANONYMOUS_PREFIX),
        profileImageUrl = profileImageUrl,
    )

private const val ANONYMOUS_PREFIX = "익명의 "