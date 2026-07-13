package com.haphap.app.presentation.calendar.type

import com.haphap.app.core.designsystem.type.PresentChanceType

sealed interface DayType {
    data class InMonth(
        val likelihood: PresentChanceType = PresentChanceType.NONE,
        val isToday: Boolean = false,
        val isSelected: Boolean = false,
    ) : DayType
    data object OutMonth : DayType
}