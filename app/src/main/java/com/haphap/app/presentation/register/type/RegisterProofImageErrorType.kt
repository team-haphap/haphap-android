package com.haphap.app.presentation.register.type

enum class RegisterProofImageErrorType(val message: String) {
    LOAD_FAILED("이미지를 불러오는데 실패했어요."),
    UNSUPPORTED_FORMAT("지원하지 않는 형식이에요."),
    SIZE_EXCEEDED("용량이 커서 업로드할 수 없어요."),
}
