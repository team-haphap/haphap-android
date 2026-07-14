package com.haphap.app.core.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toTimeFormat(pattern: String = "HH:mm"): String {
    return runCatching {
        LocalDateTime.parse(this).format(DateTimeFormatter.ofPattern(pattern))
    }.getOrDefault("")
}
