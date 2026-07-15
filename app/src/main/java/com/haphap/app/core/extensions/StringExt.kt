package com.haphap.app.core.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * ISO-8601 형식(yyyy-MM-dd)의 문자열을 [LocalDate]로 변환한다.
 *
 * @receiver "yyyy-MM-dd" 형식의 날짜 문자열
 * @return 파싱된 [LocalDate]
 * @throws java.time.format.DateTimeParseException 형식이 올바르지 않은 경우
 */
fun String.toLocalDate(): LocalDate = LocalDate.parse(this)

fun String.toTimeFormat(pattern: String = "HH:mm"): String {
    return runCatching {
        LocalDateTime.parse(this).format(DateTimeFormatter.ofPattern(pattern))
    }.getOrDefault("")
}