package com.haphap.app.data.model.passcertification

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PassCertificationDetailModel(
    val companyName: String = "",
    val title: String = "",
    val stageName: String = "",
    val submittedDate: String = "",
    val rejectReason: String = "",
    val imageUrls: ImmutableList<String> = persistentListOf(),
)
