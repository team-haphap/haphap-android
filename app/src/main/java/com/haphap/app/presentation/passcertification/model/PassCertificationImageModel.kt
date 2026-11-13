package com.haphap.app.presentation.passcertification.model

const val MAX_CERTIFICATION_IMAGE_COUNT = 3

data class PassCertificationImageModel(
    val uri: String,
    val isError: Boolean = false,
    val imageId: Long? = null,
)
