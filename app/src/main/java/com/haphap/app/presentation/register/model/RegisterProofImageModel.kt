package com.haphap.app.presentation.register.model

import android.net.Uri
import com.haphap.app.presentation.register.type.RegisterProofImageErrorType

const val MAX_PROOF_IMAGE_COUNT = 3

data class RegisterProofImageModel(
    val uri: Uri,
    val fileName: String,
    val fileSize: String,
    val error: RegisterProofImageErrorType? = null,
)
