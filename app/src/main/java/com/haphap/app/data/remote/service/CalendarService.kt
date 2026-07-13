package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.calendar.CalendarPostingsResponseDto
import com.haphap.app.data.remote.dto.calendar.CalendarResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CalendarService {
    @GET("api/v1/calendar")
    suspend fun getCalendar(
        @Query("date") date: String,
    ): BaseResponse<CalendarResponseDto>

    @GET("api/v1/calendar/postings")
    suspend fun getCalendarPostings(
        @Query("date") date: String,
    ): BaseResponse<CalendarPostingsResponseDto>
}
