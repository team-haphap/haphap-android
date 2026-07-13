package com.haphap.app.data.remote.datasource.impl.calendar

import com.haphap.app.data.remote.datasource.api.calendar.CalendarDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.calendar.CalendarPostingsResponseDto
import com.haphap.app.data.remote.dto.calendar.CalendarResponseDto
import com.haphap.app.data.remote.service.CalendarService
import javax.inject.Inject

class CalendarDataSourceImpl @Inject constructor(
    private val calendarService: CalendarService,
) : CalendarDataSource {
    override suspend fun getCalendar(date: String): BaseResponse<CalendarResponseDto> {
        return calendarService.getCalendar(date)
    }
    override suspend fun getCalendarPostings(date: String): BaseResponse<CalendarPostingsResponseDto> {
        return calendarService.getCalendarPostings(date)
    }
}
