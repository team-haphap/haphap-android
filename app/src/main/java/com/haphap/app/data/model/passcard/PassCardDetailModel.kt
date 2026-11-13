package com.haphap.app.data.model.passcard

data class PassCardDetailModel(
    val id: Int,
    val userName: String,
    val companyName: String,
    val recruitName: String,
    val logoUrl: String,
    val backgroundImageUrl: String,
    val stageName: String,
    val stages: List<PassCardDetailChipListModel>,
)