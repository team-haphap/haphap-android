package com.haphap.app.data.mapper.mypage

import com.haphap.app.data.model.myapplication.MyApplicationListModel
import com.haphap.app.data.model.myapplication.MyApplicationModel
import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.remote.dto.mypage.MyApplicationDto
import com.haphap.app.data.remote.dto.mypage.MyApplicationResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import kotlinx.collections.immutable.toImmutableList

fun MyPageResponseDto.toModel(): MyPageModel =
    MyPageModel(
        name = name,
        anonymousName = anonymousName,
        email = email,
        profileImageUrl = profileImageUrl,
    )

fun MyApplicationResponseDto.toModel(): MyApplicationListModel =
    MyApplicationListModel(
        inProgressCount = counts.inProgressCount,
        closedCount = counts.allClosedCount,
        applicationList = registrations.map { it.toModel() }.toImmutableList(),
    )

private fun MyApplicationDto.toModel(): MyApplicationModel =
    MyApplicationModel(
        postingId = postingId,
        companyName = companyName,
        title = title,
        logoImageUrl = logoImageUrl,
        currentStage = currentState,
        result = myResult,
    )
