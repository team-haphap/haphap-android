package com.haphap.app.presentation.myapplication.type

import com.haphap.app.data.model.myapplication.MyApplicationFilter

enum class MyApplicationFilterType(
    val label: String,
) {
    ALL("전체"),
    IN_PROGRESS("전형 진행중"),
    CLOSED("모든 전형 마감"),
}

fun MyApplicationFilterType.toFilter(): MyApplicationFilter =
    when (this) {
        MyApplicationFilterType.ALL -> MyApplicationFilter.ALL
        MyApplicationFilterType.IN_PROGRESS -> MyApplicationFilter.IN_PROGRESS
        MyApplicationFilterType.CLOSED -> MyApplicationFilter.CLOSED
    }
