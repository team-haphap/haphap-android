package com.haphap.app.data.mapper.mypage

import com.haphap.app.core.extensions.toTimeFormat
import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.model.mypage.MyPageSummaryModel
import com.haphap.app.data.model.mypage.MyRecentApplicationModel
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageSummaryResponseDto
import com.haphap.app.data.remote.dto.mypage.MyRegistrationPreviewDto

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

fun MyRegistrationPreviewDto.toModel(): MyRecentApplicationModel =
    MyRecentApplicationModel(
        id = postingId,
        title = title,
        stageName = currentState,
        participantCount = applicantCount,
        appliedDate = appliedAt.toTimeFormat("yyyy.MM.dd"),
        logoImageUrl = logoImageUrl,
    )
