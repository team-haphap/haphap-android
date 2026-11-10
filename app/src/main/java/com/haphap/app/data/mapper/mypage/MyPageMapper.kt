package com.haphap.app.data.mapper.mypage

import com.haphap.app.data.model.mypage.AccountInfoModel
import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.remote.dto.mypage.AccountInfoResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto

fun MyPageResponseDto.toModel(): MyPageModel =
    MyPageModel(
        name = name,
        anonymousName = anonymousName,
        email = email,
        profileImageUrl = profileImageUrl,
    )

fun AccountInfoResponseDto.toModel(): AccountInfoModel =
    AccountInfoModel(
        name = name,
        email = email,
        loginProvider = "카카오",
    )
