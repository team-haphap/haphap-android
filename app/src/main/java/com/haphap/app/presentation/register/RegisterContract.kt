package com.haphap.app.presentation.register

enum class RegisterStep {
    ANNOUNCE_AND_PROCESS,
    RESULT,
    DATE_AND_CHANNEL,
    CONFIRM,
    COMPLETE,
}

enum class NotificationChannelType(val text: String) {
    SMS("문자"),
    EMAIL("메일"),
    // TODO: 기획 확인 후 더 추가
}

sealed interface RegisterEntryPoint {
    data object Home: RegisterEntryPoint
    data class JobDetail(val jobId: Long) : RegisterEntryPoint
}