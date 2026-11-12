package com.haphap.app.data.mapper.mypage

import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.model.mypage.MyPageSummaryModel
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageSummaryResponseDto

fun MyPageResponseDto.toModel(): MyPageModel =
    MyPageModel(
        name = name,
        anonymousName = anonymousName,
        email = email,
        profileImageUrl = profileImageUrl,
    )

fun MyPageSummaryResponseDto.toModel(): MyPageSummaryModel =
    MyPageSummaryModel(
        nickname = member.nickname,
        profileImageUrl = member.profileImageUrl,
        point = member.point,
        rejectedCertificationCount = passCertification.rejectedCount,
        approvedCertificationCount = passCertification.approvedCount,
        hasUpcomingAnnouncement = hasUpcomingAnnouncement,
    )
