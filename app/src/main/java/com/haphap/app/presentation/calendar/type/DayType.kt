package com.haphap.app.presentation.calendar.type

sealed interface DayType {
    data class InMonth(
        val presentChance: PresentChance = PresentChance.NONE,
        val isToday: Boolean = false,
        val isSelected: Boolean = false,
    ) : DayType
    data object OutMonth : DayType
}