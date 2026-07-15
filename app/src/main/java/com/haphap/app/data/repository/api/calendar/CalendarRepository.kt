package com.haphap.app.data.repository.api.calendar

import com.haphap.app.data.model.calendar.CalendarModel
import com.haphap.app.data.model.calendar.CalendarPostingModel

interface CalendarRepository {
    suspend fun getCalendar(date: String): Result<List<CalendarModel>>

    suspend fun getCalendarPostings(date: String): Result<List<CalendarPostingModel>>
}
