package com.haphap.app.core.extensions

import java.text.BreakIterator
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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

private val DOT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
private val KOREAN_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("HH시 mm분")

/**
 * ISO-8601 형식(yyyy-MM-dd)의 날짜 문자열을 "yyyy.MM.dd" 형식으로 변환한다.
 *
 * @receiver "yyyy-MM-dd" 형식의 날짜 문자열 (예: "2026-06-11")
 * @return "yyyy.MM.dd" 형식의 문자열 (예: "2026.06.11")
 * @throws java.time.format.DateTimeParseException 형식이 올바르지 않은 경우
 */
fun String.toDotDateFormat(): String = LocalDate.parse(this).format(DOT_DATE_FORMATTER)

/**
 * ISO-8601 형식(HH:mm)의 시간 문자열을 "HH시 mm분" 형식으로 변환한다.
 *
 * @receiver "HH:mm" 형식의 시간 문자열 (예: "21:33")
 * @return "HH시 mm분" 형식의 문자열 (예: "21시 33분")
 * @throws java.time.format.DateTimeParseException 형식이 올바르지 않은 경우
 */
fun String.toKoreanTimeFormat(): String = LocalTime.parse(this).format(KOREAN_TIME_FORMATTER)

/**
 * 사람이 보는 글자(grapheme cluster) 단위로 길이를 센다.
 *
 * [CharSequence.length]는 UTF-16 Char 개수를 세기 때문에 이모지 하나가 2개 이상으로 세어지는 반면,
 * 이 함수는 "😀", "👍🏻", "👨‍👩‍👧" 같은 이모지도 1글자로 센다.
 *
 * @receiver 길이를 셀 문자열
 * @return 글자 수
 */
fun CharSequence.graphemeLength(): Int {
    val iterator = BreakIterator.getCharacterInstance().apply { setText(this@graphemeLength.toString()) }

    var count = 0
    while (iterator.next() != BreakIterator.DONE) {
        count++
    }

    return count
}