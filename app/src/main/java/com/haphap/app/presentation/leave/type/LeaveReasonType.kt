package com.haphap.app.presentation.leave.type

enum class LeaveReasonType(val text: String, val serverValue: String) {
    JOB_SEARCH_FINISHED("취업 준비 활동이 끝났어요.", "JOB_PREPARATION_FINISHED"),
    LACK_OF_INFORMATION("원하는 정보가 부족해요.", "NOT_ENOUGH_INFO"),
    RARELY_USED("서비스를 잘 사용하지 않아요.", "LOW_USAGE"),
    PRIVACY_CONCERN("개인정보(보안) 유출이 걱정돼요.", "PRIVACY_CONCERN"),
    ETC("기타 (직접 입력)", "ETC"),
}
