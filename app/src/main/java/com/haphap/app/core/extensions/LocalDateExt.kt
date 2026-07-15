package com.haphap.app.core.extensions

import com.haphap.app.presentation.calendar.type.DayType
import com.haphap.app.core.designsystem.type.PresentChanceType
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * 날짜를 캘린더 그리드에 표시하기 위한 [DayType]으로 변환한다.
 *
 * [day]가 [yearMonth]에 속하지 않으면 [DayType.OutMonth]를,
 * 속하면 오늘/선택 여부와 [likelihood]를 담은 [DayType.InMonth]를 반환한다.
 *
 * @receiver 판단 기준이 되는 날짜(오늘/선택 여부 비교에 사용)
 * @param day [yearMonth] 소속 여부를 판단할 날짜
 * @param yearMonth 현재 캘린더가 표시 중인 연월
 * @param today 오늘 날짜
 * @param selectedDate 사용자가 선택한 날짜 (없으면 null)
 * @param likelihood 해당 날짜의 선물 등장 확률
 * @return 계산된 [DayType]
 */
fun LocalDate.toDayType(
    day: LocalDate,
    yearMonth: YearMonth,
    today: LocalDate,
    selectedDate: LocalDate?,
    likelihood: PresentChanceType,
): DayType {
    return if (YearMonth.from(day) == yearMonth) {
        DayType.InMonth(
            likelihood = likelihood,
            isToday = this == today,
            isSelected = this == selectedDate,
        )
    } else {
        DayType.OutMonth
    }
}

/**
 * 날짜를 "yyyy-MM-dd" 형식의 문자열로 변환한다. (예: "2026-07-13")
 *
 * @receiver 변환할 날짜
 * @return "yyyy-MM-dd" 형식의 문자열
 */
fun LocalDate.toDateString(): String = this.format(DateTimeFormatter.ISO_LOCAL_DATE)