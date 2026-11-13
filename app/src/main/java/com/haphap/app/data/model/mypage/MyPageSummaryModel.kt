package com.haphap.app.data.model.mypage

data class MyPageSummaryModel(
    val nickname: String,
    val profileImageUrl: String,
    val point: Int,
    val rejectedCertificationCount: Int,
    val approvedCertificationCount: Int,
    val hasUpcomingAnnouncement: Boolean,
)
