package com.haphap.app.data.remote.dto.mypage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageSummaryResponseDto(
    @SerialName("member")
    val member: MyPageMemberDto,
    @SerialName("passCertification")
    val passCertification: MyPagePassCertificationDto,
    @SerialName("hasUpcomingAnnouncement")
    val hasUpcomingAnnouncement: Boolean,
)

@Serializable
data class MyPageMemberDto(
    @SerialName("memberId")
    val memberId: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("point")
    val point: Int,
)

@Serializable
data class MyPagePassCertificationDto(
    @SerialName("rejectedCount")
    val rejectedCount: Int,
    @SerialName("approvedCount")
    val approvedCount: Int,
)
