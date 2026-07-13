package com.haphap.app.core.extensions

import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * 연월을 "yyyy-MM" 형식의 문자열로 변환한다. (예: "2026-07")
 *
 * @receiver 변환할 연월
 * @return "yyyy-MM" 형식의 문자열
 */
fun YearMonth.toDateString(): String = this.format(DateTimeFormatter.ofPattern("yyyy-MM"))