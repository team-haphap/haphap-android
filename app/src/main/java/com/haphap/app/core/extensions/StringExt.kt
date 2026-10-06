package com.haphap.app.core.extensions

import java.text.BreakIterator
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