package com.haphap.app.data.mapper.mypage

import com.haphap.app.core.extensions.toTimeFormat
import com.haphap.app.data.model.myapplication.MyApplicationListModel
import com.haphap.app.data.model.myapplication.MyApplicationModel
import com.haphap.app.data.model.mypage.AccountInfoModel
import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.model.mypage.MyPageProfileModel
import com.haphap.app.data.model.mypage.MyPageSummaryModel
import com.haphap.app.data.model.mypage.MyRecentApplicationModel
import com.haphap.app.data.remote.dto.mypage.AccountInfoResponseDto
import com.haphap.app.data.remote.dto.mypage.MyApplicationDto
import com.haphap.app.data.remote.dto.mypage.MyApplicationResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageSummaryResponseDto
import com.haphap.app.data.remote.dto.mypage.MyRegistrationPreviewDto
import kotlinx.collections.immutable.toImmutableList

private const val CLOSED_STAGE_LABEL = "마감"

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
        currentStage = currentState ?: CLOSED_STAGE_LABEL,
        isClosed = currentState == null || currentState == CLOSED_STAGE_LABEL,
        result = myResult,
    )

fun AccountInfoResponseDto.toModel(): AccountInfoModel =
    AccountInfoModel(
        name = name,
        email = email,
        loginProvider = when (provider) {
            "KAKAO" -> "카카오"
            else -> provider
        },
    )

fun MyPageResponseDto.toProfileModel(): MyPageProfileModel =
    MyPageProfileModel(
        nickname = anonymousName.removePrefix(ANONYMOUS_PREFIX),
        profileImageUrl = profileImageUrl,
    )

private const val ANONYMOUS_PREFIX = "익명의 "
