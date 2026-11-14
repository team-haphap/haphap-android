package com.haphap.app.data.model.mypage

data class MyRecentApplicationModel(
    val id: Int,
    val title: String,
    val stageName: String,
    val participantCount: Int,
    val appliedDate: String,
    val logoImageUrl: String,
)
