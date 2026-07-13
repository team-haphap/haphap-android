package com.haphap.app.data.repository.impl.calendar

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.calendar.toModel
import com.haphap.app.data.model.calendar.CalendarModel
import com.haphap.app.data.model.calendar.CalendarPostingModel
import com.haphap.app.data.remote.datasource.api.calendar.CalendarDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.calendar.CalendarRepository
import jakarta.inject.Inject


class CalendarRepositoryImpl @Inject constructor(
    private val calendarDataSource: CalendarDataSource,
): CalendarRepository {
    override suspend fun getCalendar(date: String): Result<List<CalendarModel>> =
        suspendRunCatching {
            val response = calendarDataSource.getCalendar(date).checkData()

            response.toModel()
        }

    override suspend fun getCalendarPostings(date: String): Result<List<CalendarPostingModel>> =
        suspendRunCatching {
            val response = calendarDataSource.getCalendarPostings(date).checkData()

            response.toModel()
        }
}