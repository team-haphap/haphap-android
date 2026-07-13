package com.haphap.app.data.remote.datasource.api.calendar

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.calendar.CalendarPostingsResponseDto
import com.haphap.app.data.remote.dto.calendar.CalendarResponseDto

interface CalendarDataSource{
    suspend fun getCalendar(date: String): BaseResponse<CalendarResponseDto>

    suspend fun getCalendarPostings(date: String): BaseResponse<CalendarPostingsResponseDto>
}
